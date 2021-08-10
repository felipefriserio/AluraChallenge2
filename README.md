<!-- PROJECT SHIELDS -->
[![LinkedIn][linkedin-shield]][linkedin-url]


<div align="center">
	<img src="https://github.com/felipefriserio/AluraChallenge2/blob/main/src/main/resources/static/alura_logo.svg" alt="logo">
  	<h1>Alura Challenge - Backend</h1>
</div>
<div>
    <details open="open">
      <summary>Sumário</summary>
      <ol>
        <li>
          <a href="#sobre-o-projeto">Sobre o projeto</a>
          <ul>
            <li><a href="#tecnologias">Tecnologias</a></li>
          </ul>
        </li>
        <li><a href="#subindo-a-app">Subindo a app</a></li>
        <li><a href="#postman">Postman</a></li>
        <li><a href="#desafios">Desafios</a></li>
        <li>
          <a href="#autenticacao">Autenticação</a>
          <ul>
            <li><a href="#gerando-o-token">Gerando o Token</a></li>
            <li><a href="#usando-o-token">Usando o Token</a></li>
          </ul>
        </li>
        <li><a href="#swagger">Swagger</a></li>
        <li><a href="#h2-database">H2 Database</a></li>
      </ol>
    </details>
</div>

## Sobre o projeto
Desenvolvimento de uma API REST de videos para o <a href="https://www.alura.com.br/challenges/back-end/">Challenge Alura - Backend</a>

## Tecnologias 
- Java 8
- JPA
- Spring Data
- Spring Security
- JUnit
- Lombok
- H2 Database (banco em memória)
- Bean Validation
- Swagger 

# Subindo a app
Comando maven para rodar os testes e subir a aplicação:
```
mvn test spring-boot:run
```

## Postman
É possível importar todas as requisições postaman da API. O arquivo se encontra em : src > main > java > data

## Autenticacao
Com exceção do endpoint "/videos/free", para todos os outros endpoints é necessário enviar um token de autenticação.

## Gerando o token
Envie uma requisição POST para o endpoint :
```
/autenticacao
```
Com as seguintes credenciais JSON no body : 
```
{
"email" : "admin@alura.com.br",
"senha" : "backend"
}
```

## Usando o token
Com o token em mãos, agora basta incluir um atributo 'Authorization' no header de cada requisição.

Exemplo:
<img src="https://github.com/felipefriserio/AluraChallenge2/blob/main/src/main/resources/static/requisicao.png" alt="requisicao">

## Swagger
A documentação da API pode ser vista no endpoint : 
```
/swagger-ui.html
```

## H2 Database
Para acessar o banco de dados em memória : 
```
/h2-console
```

## Desafios
| Semana | Tarefa | Finalizado |
| :---:   | :---  | :---:  |
| 1 | Banco de dados - Armazenar no banco de dados as seguintes informações sobre os vídeos. id, titulo,descricao e url | OK |
| 1 | Testes no Postman - Verificar Status code das requisições GET, POST, PUT e DELETE | OK |
| 1 | Regra de negócio - Todos os campos de vídeos devem ser obrigatórios e validados. | OK |                                         
| 1 | GET - Atender uma requisição GET que retorne um único vídeo, caso o ID do vídeo seja válido, com o seguinte endpoint:  /videos/1 - Caso o vídeo não esteja cadastrado, retornar uma mensagem de Não encontrado. | OK |
| 1 | DELETE - Requisição para deletar um vídeo pelo ID de negócio - Retornar uma mensagem de sucesso ou falha. | OK |
| 1 | PUT / PATCH - Requisição para atualizar um vídeo pelo ID - Retornar um Json com informações do filme atualizado. | OK |
| 1 | POST - Requisição para criar um novo vídeo - Atender uma requisição POST que crie um novo vídeo, caso todos os campos estejam preenchidos e validados. - Retornar um Json com informações do vídeo criado. | OK |
| 1 | GET - Requisição que exiba todos os videos - Atender uma requisição GET que exiba todos os vídeos com o seguinte endpoint: /videos  | OK |
| 2 | Banco de dados -Armazenar no banco de dados as seguintes informações sobre categorias, id, titulo e cor | OK |                                         
| 2 | Testes de unidade e testes de Integração - Criar testes de unidade para os modelos e controller. Crie testes de integração. | - |                                            
| 2 | Requisição para exibir vídeos por categoria - Criar uma rota GET relacionando categorias e videos: GET categorias/:id/videos/ | OK |                                            
| 2 | Dados da categoria - Uma nova categoria não pode ser criada caso tenha algum campo vazio. Caso em branco, informar: O campo é obrigatório | OK |
| 2 | GET - Atender uma requisição GET que exiba todos as categorias com o seguinte endpoint: /categorias/ | OK |
| 2 | Filmes sem categoria definida - A categoria com ID = 1, deve chamar LIVRE e caso ela não seja especificada na criação do vídeo, vamos atribuir o ID = 1 | OK |                                         
| 2 | Buscar vídeos por título - Criar uma rota que busque vídeos por nome via query parameters, por exemplo: GET /videos/?search=jogos | OK |                                           
| 2 | Agrupar vídeos e categorias - Implemente uma relação entre vídeos e categorias, atribuindo para cada vídeo uma categoria | OK |
| 2 | DELETE - Atender uma requisição capaz de deletar uma categoria pelo ID, como mostra o endpoint de exemplo: categorias/1/ | OK |
| 2 | PUT / PATCH - Atender uma requisição capaz de atualizar um ou mais campos de uma categoria. | OK |
| 2 | POST - Atender uma requisição POST que crie uma nova categoria, caso todos os campos estejam preenchidos e validados. Retornar um Json com informações da categoria criada. | OK |
| 3 | Regra de negócio - Paginação -  Adicione nas requisições GET em ambos os modelos, tanto vídeos como categoria uma paginação que retorne 5 itens por página. Por exemplo: GET /videos/?page=2  | OK |
| 3 | Autenticação - Para garantir a segurança dos dados, implemente algum tipo de autenticação, para que só os usuários autenticados possam acessar as rotas de GET, POST, PUT e DELETE. Caso a autenticação não seja válida, retornar uma mensagem informando Não autorizado ou Credenciais inválidas. Caso usuário e senha inválido, informar Usuário e senha inválido | OK | 
| 3 | Regra de negócio - Requisição Free - Criar o seguinte endpoint com um número fixo de filmes disponível, sem a necessidade de autenticação: GET /videos/free | OK |


<!-- MARKDOWN LINKS-->
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/felipe-riserio/