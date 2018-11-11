# AdaLovelaceChat (Java)
<br>Tipo de Proyecto: Proyecto de Universidad.
<br>Estado: Terminado, en plan de reescribirlo y mejorarlo en C#.
<br>Descripción: Este proyecto consta de 2 programas, el servidor y el cliente.
<br>El servidor acepta las conexiones entrantes de los clientes, creando un thread por cada cliente. cada thread al enviar un mensaje, lo envía al asistente para que este lo procese, y en caso de que corresponda, envíe un mensaje en respuesta. 
<br>En el cliente se utiliza una interfaz gráfica donde se muestra una lista de usuarios conectados y salas publicas creadas, el usuario puede seleccionar uno de estos para crear una sala, una vez creada puede cambiar el nombre de esta o bien cambiar su visibilidad (pública o privada), también puede invitar a otro usuario usando @. Además, el usuario puede llamar al asistente por ej. “hola @Ada” o “buenos días @Ada”, el nombre es indistinto ya que el asistente recibe el nombre con el que fue llamado. Una vez saludado el usuario puede pedirle cualquier acción y el asistente responderá la petición o informará que no puede realizarlo. 
<br>
<br>Metodologías aplicadas:
<br>TDD, Cadena de Responsabilidad y Utilización de Junit Test para verificar comportamientos y a modo de documentación.
<br>
<br>Temas aprendidos: 
<br>Sockets (tanto cliente como servidor), Clase Junit Test, Programación MultiThreading, Programación con Cadena de Responsabilidad para un código más prolijo y entendible, Interfaz Gráfica con Swing, Utilización de APIs y SQLite para guardar usuarios, respuestas y peticiones del asistente, eventos y deudas.
<br>
<br>Tareas que puede desempeñar el Asistente:
<br>El asistente puede mantener una relación cordial con el usuario o bien contestar de forma bruta, según sea el trato del usuario para con el asistente.
<br>También puede buscar información de lo que se le pida en Wikipedia, realizar cálculos de complejidad media (puede sumar, restar, dividir, multiplicar, realizar raíces, potencias, logaritmos y puede manejar un nivel alto de paréntesis), realiza conversión de unidades tanto del sistema métrico como el sistema imperial, puede entregar información sobre el clima actual basado en la ubicación de la ir, para pasar el tiempo tiene un juego de mayor o menor, además de tener URL de memes en la base de datos, trae gifs de la página giphy según un criterio o totalmente aleatorio, además de imágenes aleatorias de la página 9gag, también cuenta con una base de datos de deudas donde se puede llevar un control de deudas personales 
