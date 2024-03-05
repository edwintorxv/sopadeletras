Para hacer la prueba de la APi una ve cloando el repositorio, 
1). Proceda a abrir el proyecto con el IDE eclipse, 
2). Inicie el proyecto dando clic derecho sobre el y en la opcion 'run as" seleccione 'Spring Boot App'
3). Para consumir desde postman utilice el siguiente endpoint: localhost:8080/search-horandvert o localhost:8080/search
4). En postman configure las peticiones de forma POST
5). Para comsumir desde swagger ingrese a esta url http://localhost:8080/swagger-ui/index.html
6). Para ambos casos Postman y Swgger use el siguiente JSON para pasar como parametro

{
"searchword": "abcdefghijklmnopqrstuvwxyzabcdef",
"rows": 4,
"word": "bjrz"
}
