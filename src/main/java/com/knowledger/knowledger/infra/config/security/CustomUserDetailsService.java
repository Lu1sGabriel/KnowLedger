package com.knowledger.knowledger.infra.config.security;

import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço personalizado para carregamento de detalhes do usuário no sistema.
 * Implementa a interface {@link UserDetailsService} do Spring Security, permitindo autenticar e buscar informações
 * detalhadas sobre o usuário com base no e-mail fornecido. Esta classe é essencial para o processo de autenticação
 * e autorização, pois carrega as credenciais e as permissões (roles) do usuário.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository _iUserRepository;

    /**
     * Construtor da classe CustomUserDetailsService.
     *
     * @param userRepository repositório de usuários para busca dos dados de autenticação.
     */
    public CustomUserDetailsService(IUserRepository userRepository) {
        _iUserRepository = userRepository;
    }

    /**
     * Carrega o usuário com base no e-mail fornecido. Esse método é invocado automaticamente durante o processo de
     * autenticação do Spring Security. Ele busca o usuário no repositório e, se encontrado, retorna um objeto
     * {@link UserDetails} contendo as credenciais e permissões (roles) do usuário. Caso o usuário não seja encontrado,
     * uma exceção {@link UsernameNotFoundException} é lançada.
     *
     * @param email o e-mail do usuário a ser carregado.
     * @return uma instância de {@link UserDetails} com as informações de autenticação do usuário.
     * @throws UsernameNotFoundException se o usuário com o e-mail especificado não for encontrado no repositório.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userEntity = _iUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        var authority = new SimpleGrantedAuthority(userEntity.getRole().getName());

        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(authority)
                .build();
    }

}