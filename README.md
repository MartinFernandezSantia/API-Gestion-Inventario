### Recorrido de los datos en la API
1. Hay minimamente 4 endpoints por controlador:
   - Para crear un objeto, request de tipo *POST* en *localhost:8080/enpoint*
   - Para obtener un objeto por ID, request de tipo *GET* en *localhost:8080/enpoint/{id}*
   - Para obtener todos los objetos, request de tipo *GET* en *localhost:8080/enpoint*
   - Para eliminar un objeto por ID, request de tipo *DELETE* en *localhost:8080/enpoint/{id}*
   - Para actualizar un objeto, request de tipo *PUT* en *localhost:8080/enpoint*

2. Los endpoints para **crear** y **actualizar** reciben un JSON con los datos necesarios y manejan dichos datos a través de un DTO o un Modelo.
3. Si alguno de los atributos del DTO es una FK, entonces se valida antes de continuar.
4. Si se recibio un DTO, entonces se utiliza el *Traductor* correspondiente para convertirlo en un objeto del Modelo.
5. El objeto de Modelo es pasado como parametro al metodo de *Servicio* que corresponda.
6. En el Servicio se ejecuta toda la logica necesaria, se transforma el objeto de Modelo nuevamente en DTO y se envia al DAO
7. El DAO se encarga de interactuar con la base de datos y retornar si el proceso fallo o tuvo exito.
8. La respuesta del DAO, es recivida por el Servicio y reenviada al Controlador.
9. El Controlador recive la respuesta del Servicio y en base a ella retorna los datos necesarios al cliente o un Error.