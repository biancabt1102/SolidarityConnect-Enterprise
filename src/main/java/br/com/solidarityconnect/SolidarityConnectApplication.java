package br.com.solidarityconnect;

import br.com.solidarityconnect.models.Alimento;
import br.com.solidarityconnect.models.Endereco;
import br.com.solidarityconnect.models.Usuario;
import br.com.solidarityconnect.repository.AlimentoRepository;
import br.com.solidarityconnect.repository.DoacaoRepository;
import br.com.solidarityconnect.repository.EnderecoRepository;
import br.com.solidarityconnect.repository.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SolidarityConnectApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SolidarityConnectApplication.class, args);

		AlimentoRepository alimentoRepository = context.getBean(AlimentoRepository.class);
		EnderecoRepository enderecoRepository = context.getBean(EnderecoRepository.class);
		UsuarioRepository usuarioRepository = context.getBean(UsuarioRepository.class);

		// Realizar testes

		//Alimentos
		saveAlimento(alimentoRepository, 6L, "Arroz", 10, "Perecível", LocalDate.of(2023, 6, 5));
		findByIdAlimento(alimentoRepository, 6L);
		updateAlimento(alimentoRepository, 6L);
		findAllAlimentos(alimentoRepository);
		testFindByTipoAlimento(alimentoRepository, "Perecível");
		testFindByNomeAlimentoContaining(alimentoRepository, "Arroz");
		testFindByQuantidadeAlimentoGreaterThan(alimentoRepository, 10);
		deleteAlimento(alimentoRepository, 6L);

		//Endereço
		saveEndereco(enderecoRepository, 6L, "Av. Teste", 123, "98765-432", "Centro", "SP", null, null);
		findByIdEndereco(enderecoRepository, 6L);
		updateEndereco(enderecoRepository, 6L);
		findAllEnderecos(enderecoRepository);
		testFindByLogradouroEnderecoContaining(enderecoRepository, "Avenida");
		testFindByBairroEndereco(enderecoRepository, "Centro Gastronômico");
		testFindByNomeUsuario(enderecoRepository, "SuperFresh");
		deleteEndereco(enderecoRepository, 6L);

		//Usuário
		saveUsuario(usuarioRepository, 6L, "12.345.678/9012-34", "dia@example.com", "Dia", "dia12345", "(41) 98765-4321");
		findByIdUsuario(usuarioRepository, 6L);
		updateUsuario(usuarioRepository, 6L);
		findAllUsuarios(usuarioRepository);
		testFindByEmail(usuarioRepository, "contato@labellapasta.com");
		testFindByNomeUsuarioContaining(usuarioRepository, "Su");
		testFindByCnpj(usuarioRepository, "45.678.901/2345-67");
		deleteUsuario(usuarioRepository, 6L);
	}

	// Métodos para Alimentos
	private static void saveAlimento(AlimentoRepository alimentoRepository, Long id, String nome, int quantidade, String tipo, LocalDate date) {
		System.out.println("\n###############################################");
		Alimento novoAlimento = new Alimento();
		novoAlimento.setIdAlimento(id);
		novoAlimento.setQuantidadeAlimento(quantidade);
		novoAlimento.setNomeAlimento(nome);
		novoAlimento.setTipoAlimento(tipo);
		novoAlimento.setValidadeAlimento(date);

		alimentoRepository.save(novoAlimento);

		System.out.println("\nNovo Alimento adicionado com sucesso:\n");
		System.out.println(novoAlimento);
	}

	private static void findByIdAlimento(AlimentoRepository alimentoRepository, Long id) {
		System.out.println("\n###############################################");
		System.out.println("\nInformações do alimento com id " + id + ":\n");
		Alimento alimento = alimentoRepository.findById(id)
				.orElse(null);

		if (alimento != null) {
			System.out.println(alimento);
		} else {
			System.out.println("\nAlimento não encontrado!");
		}
	}

	private static void updateAlimento(AlimentoRepository alimentoRepository, Long id) {
		System.out.println("\n###############################################");
		Alimento alimento = alimentoRepository.findById(id)
				.orElse(null);

		if (alimento != null) {
			alimento.setNomeAlimento("Novo Alimento");
			alimentoRepository.save(alimento);

			System.out.println("\nAlimento atualizado com sucesso!");
		} else {
			System.out.println("\nAlimento não encontrado!");
		}
	}

	private static void findAllAlimentos(AlimentoRepository alimentoRepository) {
		System.out.println("\n###############################################");
		List<Alimento> alimentos = alimentoRepository.findAll();
		System.out.println("\nLista de todos os alimentos:\n");
		alimentos.forEach(System.out::println);
	}

	public static void testFindByTipoAlimento(AlimentoRepository alimentoRepository, String tipo) {
		List<Alimento> alimentos = alimentoRepository.findByTipoAlimento(tipo);
		System.out.println("\n###############################################");
		System.out.println("\nAlimentos por tipo de alimento (" + tipo + "):\n");
		alimentos.forEach(System.out::println);
	}

	public static void testFindByNomeAlimentoContaining(AlimentoRepository alimentoRepository, String nome) {
		List<Alimento> alimentos = alimentoRepository.findByNomeAlimentoContaining(nome);
		System.out.println("\n###############################################");
		System.out.println("\nAlimentos por nome de alimento contendo '" + nome + "':\n");
		alimentos.forEach(System.out::println);
	}


	public static void testFindByQuantidadeAlimentoGreaterThan(AlimentoRepository alimentoRepository, int quantidade) {
		List<Alimento> alimentos = alimentoRepository.findByQuantidadeAlimentoGreaterThan(quantidade);
		System.out.println("\n###############################################");
		System.out.println("\nAlimentos com quantidade maior que " + quantidade + ":\n");
		alimentos.forEach(System.out::println);
	}

	private static void deleteAlimento(AlimentoRepository alimentoRepository, Long id) {
		alimentoRepository.deleteById(id);
		System.out.println("\n###############################################");
		Alimento alimento = alimentoRepository.getReferenceById(id);
		System.out.println(alimento != null ? "\nAlimento deletado com sucesso!\n" : "\nAlimento não encontrado!\n");
	}

	// Métodos para Endereço
	private static void saveEndereco(EnderecoRepository enderecoRepository, Long id, String logradouro, int numero, String cep, String bairro, String uf, String complemento, Usuario usuario) {
		System.out.println("\n###############################################");
		Endereco novoEndereco = new Endereco();
		novoEndereco.setIdEndereco(id);
		novoEndereco.setLogradouroEndereco(logradouro);
		novoEndereco.setNumeroEndereco(numero);
		novoEndereco.setCepEndereco(cep);
		novoEndereco.setBairroEndereco(bairro);
		novoEndereco.setUfEndereco(uf);
		novoEndereco.setComplementoEndereco(complemento);
		novoEndereco.setUsuario(usuario);

		enderecoRepository.save(novoEndereco);

		System.out.println("\nNovo Endereço adicionado com sucesso:\n");
		System.out.println(novoEndereco);
	}


	private static void findByIdEndereco(EnderecoRepository enderecoRepository, Long id) {
		System.out.println("\n###############################################");
		System.out.println("\nInformações do endereço com id " + id + ":\n");
		Endereco endereco = enderecoRepository.findById(id)
				.orElse(null);

		if (endereco != null) {
			System.out.println(endereco);
		} else {
			System.out.println("\nEndereço não encontrado!");
		}
	}

	private static void updateEndereco(EnderecoRepository enderecoRepository, Long id) {
		System.out.println("\n###############################################");
		Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);

		if (enderecoOptional.isPresent()) {
			Endereco endereco = enderecoOptional.get();
			endereco.setLogradouroEndereco("Novo Endereço");
			enderecoRepository.save(endereco);

			System.out.println("\nEndereço atualizado com sucesso!");
		} else {
			System.out.println("\nEndereço não encontrado!");
		}
	}

	private static void findAllEnderecos(EnderecoRepository enderecoRepository) {
		System.out.println("\n###############################################");
		List<Endereco> enderecos = enderecoRepository.findAll();
		System.out.println("\nLista de todos os endereços:\n");
		enderecos.forEach(System.out::println);

	}

	public static void testFindByLogradouroEnderecoContaining(EnderecoRepository enderecoRepository, String logradouro) {
		List<Endereco> enderecos = enderecoRepository.findByLogradouroEnderecoContaining(logradouro);
		System.out.println("\n###############################################");
		System.out.println("\nEndereços por logradouro contendo '" + logradouro + "':\n");
		enderecos.forEach(System.out::println);
	}

	public static void testFindByBairroEndereco(EnderecoRepository enderecoRepository, String bairro) {
		List<Endereco> enderecos = enderecoRepository.findByBairroEndereco(bairro);
		System.out.println("\n###############################################");
		System.out.println("\nEndereços por bairro '" + bairro +"':\n");
		enderecos.forEach(System.out::println);
	}

	public static void testFindByNomeUsuario(EnderecoRepository enderecoRepository, String nomeUsuario) {
		List<Endereco> enderecos = enderecoRepository.findByNomeUsuario(nomeUsuario);
		System.out.println("\n###############################################");
		System.out.println("\nEndereços por nome de usuário '"+ nomeUsuario +"':\n");
		enderecos.forEach(System.out::println);
	}


	private static void deleteEndereco(EnderecoRepository enderecoRepository, Long id) {
		enderecoRepository.deleteById(id);
		System.out.println("\n###############################################");
		Endereco endereco = enderecoRepository.getReferenceById(id);
		System.out.println(endereco != null ? "\nEndereço deletado com sucesso!\n" : "\nEndereço não encontrado!\n");
	}

	// Métodos Usuário
	private static void saveUsuario(UsuarioRepository usuarioRepository, Long id, String cnpj, String email, String nomeUsuario, String senha, String numeroTelefone) {
		System.out.println("\n###############################################");
		Usuario novoUsuario = new Usuario();
		novoUsuario.setIdUsuario(id);
		novoUsuario.setCnpjUsuario(cnpj);
		novoUsuario.setEmailUsuario(email);
		novoUsuario.setNomeUsuario(nomeUsuario);
		novoUsuario.setSenhaUsuario(senha);
		novoUsuario.setTelefoneUsuario(numeroTelefone);

		usuarioRepository.save(novoUsuario);

		System.out.println("\nNovo Usuário adicionado com sucesso:\n");
		System.out.println(novoUsuario);
	}

	private static void findByIdUsuario(UsuarioRepository usuarioRepository, Long id) {
		System.out.println("\n###############################################");
		System.out.println("\nInformações do usuário com id " + id + ":\n");
		Usuario usuario = usuarioRepository.findById(id)
				.orElse(null);

		if (usuario != null) {
			System.out.println(usuario);
		} else {
			System.out.println("\nUsuário não encontrado!");
		}
	}

	private static void updateUsuario(UsuarioRepository usuarioRepository, Long id) {
		System.out.println("\n###############################################");
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			usuario.setNomeUsuario("Novo Usuário");
			usuarioRepository.save(usuario);

			System.out.println("\nUsuário atualizado com sucesso!");
		} else {
			System.out.println("\nUsuário não encontrado!");
		}
	}

	private static void findAllUsuarios(UsuarioRepository usuarioRepository) {
		System.out.println("\n###############################################");
		List<Usuario> usuarios = usuarioRepository.findAll();
		System.out.println("\nLista de todos os usuários:\n");
		usuarios.forEach(System.out::println);
	}

	public static void testFindByEmail(UsuarioRepository usuarioRepository, String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		System.out.println("\n###############################################");
		System.out.println("\nUsuários por email:\n");
		System.out.println(usuario);
	}

	public static void testFindByNomeUsuarioContaining(UsuarioRepository usuarioRepository, String nomeUsuario) {
		List<Usuario> usuarios = usuarioRepository.findByNomeUsuarioContaining(nomeUsuario);
		System.out.println("\n###############################################");
		System.out.println("\nUsuários por nome de usuário contendo '" + nomeUsuario + "':\n");
		usuarios.forEach(System.out::println);
	}

	public static void testFindByCnpj(UsuarioRepository usuarioRepository, String cnpj) {
		List<Usuario> usuarios = usuarioRepository.findByCnpj(cnpj);
		System.out.println("\n###############################################");
		System.out.println("\nUsuários por CNPJ:\n");
		usuarios.forEach(System.out::println);
	}

	private static void deleteUsuario(UsuarioRepository usuarioRepository, Long id) {
		usuarioRepository.deleteById(id);
		System.out.println("\n###############################################");
		Usuario usuario = usuarioRepository.getReferenceById(id);
		System.out.println(usuario != null ? "\nUsuário deletado com sucesso!\n" : "\nUsuário não encontrado!\n");
		System.out.println("\n########################FIM########################");
	}
}
