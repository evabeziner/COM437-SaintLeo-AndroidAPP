# Mi Bookshelf

Idea inicial de una app Android nativa, privada, para catalogar mis libros propios (nombre de trabajo, no definitivo). Proyecto final de COM 437 - Desarrollo de Aplicaciones Móviles (Saint Leo University).

> Este README tiene el borrador entregado en el módulo 1. Todavía es una idea general, recién estoy arrancando con Android, así que se va a ir ajustando módulo a módulo con lo que vaya aprendiendo y con el progreso real del desarrollo.

---

## 1. Descripción del proyecto

Para el proyecto final quiero armar una app personal para organizar mi biblioteca de libros físicos. La idea surgió después de probar varias apps de este tipo que ya existen (Libib, LibraryThing, Bookshelf, Fable, Margins, Mibrary) buscando algo simple que me sirviera para lo que necesito, sin encontrar ninguna que cumpliera del todo.

La idea de mi app es simple: cargar mis libros, organizarlos en colecciones propias, dejarles una nota o comentario, y verlos ordenados como yo prefiera en cada momento. Nada de redes sociales ni de compartir con nadie: es un catálogo privado, solo para mí.

## 2. Exposición del problema

Probé varias apps de seguimiento de lectura buscando algo que me sirviera para catalogar mi biblioteca. El problema fue parecido en casi todas: o tienen anuncios, o dejan las funciones que más me interesan atrás de una suscripción paga, o empujan una parte social (agregar amigos, compartir lo que estoy leyendo, clubes de lectura) que no busco para nada.

Pero lo que más me molestó es algo más chico: ninguna me dejaba, en su versión gratuita, ordenar mi lista de libros por distintos criterios (autor, título, año, fecha en que lo agregué, puntaje) sin tener que ir tageando todo a mano. Como lectora que solo quiere un catálogo privado y prolijo de sus propios libros, ninguna app resultó ser lo que necesitaba. De ahí surgió la idea de hacer la mía, aunque sea simple.

## 3. Plataforma

Android Studio con Java, que es lo que propone el programa de la materia. Todavía no definí versión mínima de Android ni SDK exacto, eso lo voy a resolver cuando arranque a programar.

Para lo básico de la app no hace falta conexión a internet: todo se guarda en el celular. La única parte que sí necesitaría internet es una función que quiero agregar más adelante, si llego con los tiempos: escanear el código de barras (ISBN) de un libro para autocompletar sus datos. Eso lo dejo para cuando lleguemos al módulo de consumo de servicios web.

## 4. Interfaz de usuario e interfaz de administrador

Acá no hay dos roles de usuario distintos como en una idea anterior que había pensado, porque esta app la voy a usar solamente yo, para mi propia biblioteca. Lo que sí pienso separar es una pantalla principal, de uso frecuente (ver mis libros, ordenarlos, buscarlos), de una pantalla de configuración con las tareas que hago de vez en cuando, como crear una colección nueva o cambiar el criterio de orden por defecto.

Esa parte de configuración sería, en cierto sentido, la parte administradora de mi propia biblioteca, aunque termine siendo la misma persona la que usa las dos pantallas.

## 5. Funcionalidad

Lo que me gustaría que haga en su primera versión (sujeto a cambios mientras aprendo):
- Cargar un libro a mano (título, autor, año, puntaje). La fecha en que lo agregué se guarda sola.
- Ver la lista de mis libros y poder ordenarla por autor, título, año, fecha en que lo agregué o puntaje.
- Crear colecciones propias (a modo de etiquetas) y asignarle una o varias a cada libro.
- Escribir una nota o comentario personal en cada libro.
- Marcar un libro como prestado, con el nombre de a quién se lo presté, y poder marcarlo de nuevo como disponible cuando me lo devuelva.
- Sacarle una foto a la portada con la cámara del celular y guardarla junto con los datos del libro.
- Buscar un libro por título o autor.
- Todo guardado localmente en el celular, sin cuenta ni internet.

Si me da el tiempo, más adelante me gustaría sumar la posibilidad de escanear el ISBN de un libro con la cámara y que la app busque sola el título, autor y tapa mediante una API pública de libros, dejando siempre la opción de cargar todo a mano si no hay internet o el libro no aparece. Por ahora es solo una idea para más adelante.

## 6. Diseño

Todavía no armé wireframes. Sin haber tocado layouts de Android todavía, se me hizo difícil imaginar de forma realista cómo plasmar gráficamente cada pantalla, y tampoco quería terminar copiando el diseño de alguna de las apps que probé solo por no tener una idea propia. Prefiero describir en palabras lo que necesito que se vea en cada pantalla, y pensar el diseño real más adelante, cuando tenga más noción de qué es posible hacer con las herramientas de Android.

Pantallas que por ahora tengo en mente:
- Pantalla principal: lista de mis libros, con alguna forma de elegir el orden y un buscador.
- Pantalla de detalle de un libro: sus datos, la foto de portada que le saqué, la nota que le dejé, las colecciones a las que pertenece, y si está prestado y a quién.
- Pantalla para cargar un libro nuevo, a mano.
- Pantalla de colecciones, para verlas y crear nuevas.
- Pantalla de configuración, para cosas como el orden por defecto.

---

## Estado del proyecto

Borrador módulo 1, pendiente de aprobación del instructor.
