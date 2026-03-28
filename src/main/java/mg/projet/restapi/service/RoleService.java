package mg.projet.restapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.projet.restapi.exception.NotFoundException;
import mg.projet.restapi.model.Role;
import mg.projet.restapi.repository.RoleRepository;
import mg.projet.restapi.request.CreateRoleRequest;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public Role save(CreateRoleRequest roleName){
        Role role = new Role(roleName.name());
        return roleRepository.save(role);
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role findById(Long id){
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role introuvable."));
    }

    public void delete(Long id){
        findById(id);
        roleRepository.deleteById(id);
    }

    public Role update(Long id, CreateRoleRequest request){
        Role role = findById(id);
        role.setName(request.name());

        return roleRepository.save(role);
    }

}
