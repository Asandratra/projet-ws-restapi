package mg.projet.restapi.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mg.projet.restapi.assembler.HistoriqueAbonnementAssembler;
import mg.projet.restapi.assembler.HistoriqueLectureAssembler;
import mg.projet.restapi.assembler.HistoriquePaiementLivreAssembler;
import mg.projet.restapi.assembler.UserDtoAssembler;
import mg.projet.restapi.dto.HistoriqueAbonnementDto;
import mg.projet.restapi.dto.HistoriqueLectureDto;
import mg.projet.restapi.dto.HistoriquePaiementLivreDto;
import mg.projet.restapi.dto.MensualUserAmountDto;
import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.model.Role;
import mg.projet.restapi.model.User;
import mg.projet.restapi.request.AssignRoleRequest;
import mg.projet.restapi.request.ChangePasswordRequest;
import mg.projet.restapi.request.UpdateUserRequest;
import mg.projet.restapi.service.HistoriqueAbonnementService;
import mg.projet.restapi.service.HistoriqueLectureService;
import mg.projet.restapi.service.HistoriquePaiementLivreService;
import mg.projet.restapi.service.RoleService;
import mg.projet.restapi.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private HistoriqueAbonnementService historiqueAbonnementService;
    @Autowired
    private HistoriqueLectureService historiqueLectureService;
    @Autowired
    private HistoriquePaiementLivreService historiquePaiementLivreService;
    
    @Autowired
    private UserDtoAssembler userAssembler;
    @Autowired
    private HistoriqueAbonnementAssembler historiqueAbonnementAssembler;
    @Autowired
    private HistoriqueLectureAssembler historiqueLectureAssembler;
    @Autowired
    private HistoriquePaiementLivreAssembler historiquePaiementLivreAssembler;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','VIEW_USERS')")
    public List<EntityModel<UserDto>> getAllUsers() {
        List<EntityModel<UserDto>> users = userService.findAll()
            .stream()
            .map(userAssembler::toModel)
            .collect(Collectors.toList());
        
        return users;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VIEW_USERS')")
    public EntityModel<UserDto> getUserById(@PathVariable Long id) {
        return userAssembler.toModel(userService.findById(id));
    }

    @PatchMapping("/update/{id}")
    public EntityModel<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return userAssembler.toModel(userService.update(id, request));
    }

    @PatchMapping("/change-password/{id}")
    public EntityModel<UserDto> changeUserPassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request){
        return userAssembler.toModel(userService.changePassword(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return new ResponseEntity<>(Map.of("messag","Utilisateur supprimé"), HttpStatus.GONE);
    
    }
    
    @PostMapping("/assign-role")
    @PreAuthorize("hasAnyRole('ADMIN','ASSIGN_ROLE')")
    public EntityModel<UserDto> assignRole(@RequestBody AssignRoleRequest request) {
        UserDto user = userService.findById(request.idUser());
        Role role = roleService.findById(request.idRole());

        return userAssembler.toModel(userService.assignRole(user, role));
    }

    @GetMapping("/subscription-history/{id}")
    public List<EntityModel<HistoriqueAbonnementDto>> getHistoriqueAbonnement(@PathVariable Long id) {
        userService.findById(id);
        User utilisateur = new User();
        utilisateur.setId(id);
        List<HistoriqueAbonnementDto> history = historiqueAbonnementService.findAbonnementByUser(utilisateur);
        
        return history.stream()
            .map(historiqueAbonnementAssembler::toModel)
            .collect(Collectors.toList());
    }
    

    @GetMapping("/read-history/{id}")
    public List<EntityModel<HistoriqueLectureDto>> getHistoriqueLecture(@PathVariable Long id) {
        userService.findById(id);
        User utilisateur = new User();
        utilisateur.setId(id);
        List<HistoriqueLectureDto> lectures = historiqueLectureService.findLectureByUser(utilisateur);

        return lectures.stream()
            .map(historiqueLectureAssembler::toModel)
            .collect(Collectors.toList());
    }

    @GetMapping("/pay-history/{id}")
    public List<EntityModel<HistoriquePaiementLivreDto>> getHistoriquePaiement(@PathVariable Long id) {
        userService.findById(id);
        User utilisateur = new User();
        utilisateur.setId(id);
        List<HistoriquePaiementLivreDto> history = historiquePaiementLivreService.findByUtilisateur(utilisateur);

        return history.stream()
            .map(historiquePaiementLivreAssembler::toModel)
            .collect(Collectors.toList());
    }

    @GetMapping("/amount/{userId}/{annee}/{mois}")
    public MensualUserAmountDto getMontantMensuel(@PathVariable Long userId, @PathVariable int annee, @PathVariable int mois) {
        MensualUserAmountDto amount = new MensualUserAmountDto();
        amount.setUtilisateur(userService.findById(userId));
        amount.setTotalSubscription(historiqueAbonnementService.findMontantMensuelParUtilisateur(annee, mois, userId));
        amount.setTotalPayment(historiquePaiementLivreService.findMontantMensuelByUser(annee, mois, userId));
        amount.setYear(annee);
        amount.setMonth(mois);

        return amount;
    }
    
    
    
    

}
