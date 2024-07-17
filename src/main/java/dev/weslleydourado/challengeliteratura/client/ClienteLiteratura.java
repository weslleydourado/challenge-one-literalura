package dev.weslleydourado.challengeliteratura.client;

import java.util.List;
import java.util.Scanner;

import dev.weslleydourado.challengeliteratura.entity.AutorEntity;
import dev.weslleydourado.challengeliteratura.entity.LivroEntity;
import dev.weslleydourado.challengeliteratura.mapper.ConverteDados;
import dev.weslleydourado.challengeliteratura.model.Resposta;
import dev.weslleydourado.challengeliteratura.repository.AutorRepository;
import dev.weslleydourado.challengeliteratura.repository.LivroRepository;
import dev.weslleydourado.challengeliteratura.service.ConsomeApi;

public class ClienteLiteratura {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    private ConsomeApi consomeApi = new ConsomeApi();
    private ConverteDados conversor = new ConverteDados();

    private LivroRepository livroRepositorio;
    private AutorRepository autorRepositorio;

    public ClienteLiteratura(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void menu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
					Escreva a opção através do número:
						1.- Buscar livro por titulo
						2.- Lista livros registrados
						3.- Lista autores registrados
						4.- Lista autores de determinado ano
						5.- Listar livros por idioma
						0 - Sair
						""";
            System.out.println(menu);
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    buscarLivros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    buscarAutoresVivo();
                    break;
                case 5:
                    buscarPorIdiomas();
                    break;
                case 0:
                    System.out.println("Até logo, Pronto...");
                    break;
                default:
                    System.out.println("Opção inválida, escolha uma opção");
            }
        }

    }

    private void buscarLivros() {

        List<LivroEntity> libros = livroRepositorio.findAll();

        if (!libros.isEmpty()) {

            for (LivroEntity livro : libros) {
                System.out.println("\n\n---------- LIVROS -------\n");
                System.out.println(" Titulo: " + livro.getTitulo());
                System.out.println(" Autor: " + livro.getAutor().getNome());
                System.out.println(" Idioma: " + livro.getLinguagem());
                System.out.println(" Descargas: " + livro.getDescargas());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- NÃO ENCONTROU RESULTADOS ---- \n\n");
        }

    }

    private void buscarAutores() {
        List<AutorEntity> autores = autorRepositorio.findAll();

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n---------- Autores -------\n");
                System.out.println(" Nome: " + autor.getNome());
                System.out.println(" Ficha de Nacimento: " + autor.getFechaNacimento());
                System.out.println(" Ficha de Falecimento: " + autor.getFechaFalecimento());
                System.out.println(" Livros: " + autor.getLivro().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- NÃO ENCONTROU RESULTADOS---- \n\n");

        }

    }

    private void buscarAutoresVivo() {
        System.out.println("Escriva o ano para que vivo: ");
        var ano = teclado.nextInt();
        teclado.nextLine();

        List<AutorEntity> autores = autorRepositorio.findForYear(ano);

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n---------- Autores Vivos -------\n");
                System.out.println(" Nome: " + autor.getNome());
                System.out.println(" Ficha de nacimento: " + autor.getFechaNacimento());
                System.out.println(" Ficha de falecimento: " + autor.getFechaFalecimento());
                System.out.println(" Livros: " + autor.getLivro().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- NÃO ENCONTROU RESULTADOS ---- \n\n");

        }
    }

    private void buscarPorIdiomas() {

        var menu = """
				Selecione um Idioma:
					1.- Português
					2.- Ingles
				
					""";
        System.out.println(menu);
        var idioma = teclado.nextInt();
        teclado.nextLine();

        String selecao = "";

        if(idioma == 1) {
            selecao = "pt";
        } else 	if(idioma == 2) {
            selecao = "en";
        }

        List<LivroEntity> livros = livroRepositorio.findForLanguaje(selecao);

        if (!livros.isEmpty()) {

            for (LivroEntity livro : livros) {
                System.out.println("\n\n---------- LIVROS POR IDIOMA-------\n");
                System.out.println(" Titulo: " + livro.getTitulo());
                System.out.println(" Autor: " + livro.getAutor().getNome());
                System.out.println(" Idioma: " + livro.getLinguagem());
                System.out.println(" Descargas: " + livro.getDescargas());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- NÃO ENCONTROU RESULTADOS ---- \n\n");
        }


    }

    private void buscarLivroWeb() {
        Resposta dados = getDadosSerie();

        if (!dados.results().isEmpty()) {

            LivroEntity livro = new LivroEntity(dados.results().get(0));
            livro = livroRepositorio.save(livro);

        }

        System.out.println("Dados: ");
        System.out.println(dados);
    }

    private Resposta getDadosSerie() {
        System.out.println("Ingresa el nombre del livro que deseas buscar: ");
        var titulo = teclado.nextLine();
        titulo = titulo.replace(" ", "%20");
        System.out.println("Titlulo : " + titulo);
        System.out.println(URL_BASE + titulo);
        var json = consomeApi.obtenerDatos(URL_BASE + titulo);
        System.out.println(json);
        Resposta dados = conversor.obterDados(json, Resposta.class);
        return dados;
    }

}

