//AuthenticationService.java
package com.uni.PrefPet.service.auth;

import com.uni.PrefPet.repository.auth.UsuarioRepository;
import com.uni.PrefPet.config.JwtServiceGenerator;
import com.uni.PrefPet.model.Usuarios.Usuario;
import com.uni.PrefPet.model.dtos.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	@Autowired
	private UsuarioRepository loginRepository;

	@Autowired
	private JwtServiceGenerator jwtService;

	//classe do spring security que vai validar o username e password, no nosso caso email e senha
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String logar(Login login) {

		String token = this.gerarToken(login);
		return token;
	}

	public String gerarToken(Login login) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.username(),
						login.password()
						)
				);
		Usuario user = loginRepository.findByEmail(login.username()).get();
		String tokenGerado = jwtService.generateToken(user);
		return tokenGerado;
	}
}
