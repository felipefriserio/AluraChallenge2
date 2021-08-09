<!-- PROJECT SHIELDS -->
[![LinkedIn][linkedin-shield]][linkedin-url]


<div align="center">
	<img src="https://github.com/felipefriserio/AluraChallenge2/blob/main/src/main/resources/static/alura_logo.svg" alt="logo">
  	<h1>Alura Challenge - Backend</h1>
</div>
<div>
	<p align="center">
		<a href="#desafios">Desafios</a> •
	</p>
</div>Projeto para o Alura Challenge - BackEnd

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



Vídeo da semana 1 : 
https://www.youtube.com/watch?v=UiQw2HM4DtM&ab_channel=AluraCursosOnline


<!-- MARKDOWN LINKS-->
[linkedin-url]: https://linkedin.com/in/othneildrew