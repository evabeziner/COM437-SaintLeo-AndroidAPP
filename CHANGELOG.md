# Registro de cambios

Cambios del proyecto MiBookshelf, del más reciente al más viejo. Cada entrada corresponde a un módulo del curso. Al final está lo que todavía no hice y pienso hacer.

---

## Cambios actuales

### Módulo 5 - Almacenamiento de datos

**Cambio de stack.** Pasé de Java a Kotlin, y con eso cambian dos cosas más. Las pantallas van en Jetpack Compose en vez de layouts XML, y la base de datos va con Room en vez del `SQLiteOpenHelper` escrito a mano que había anotado en el foro del módulo 5. El motivo es que el ejemplo de CRUD que vemos en clase está armado así, y tener una referencia funcionando con la misma estructura pesa más que sostener el lenguaje que había puesto en el borrador. Room igual usa SQLite por debajo, así que no me estoy salteando el tema del módulo, lo estoy usando a través de la capa que la propia documentación de Android recomienda.

**Decisiones de almacenamiento**

- Definí las entidades de Room: `Libro`, `Coleccion` y `LibroColeccion` para la relación de muchos a muchos entre las dos primeras.
- Las tres opciones de la pantalla de configuración (orden por defecto, vista de la lista, mostrar prestados primero) van a `SharedPreferences` y no a una tabla, porque son valores sueltos y no registros.
- Anoté que la documentación de Android ya recomienda `DataStore` por encima de `SharedPreferences`. Para el curso sigo con `SharedPreferences`, pero es lo primero que habría que cambiar si la app siguiera después de la materia.
- Las fotos de portada van a almacenamiento interno, con la ruta en la base y el archivo afuera. Descarté meter la imagen como BLOB.
- Descarté hacer un proveedor de contenido propio, porque ninguna otra app necesita leer estos datos.
- Cerré la API mínima en 24 (Android 7.0), con `compileSdk` y `targetSdk` 36.
- Partí la lista de funcionalidades en dos grupos, lo que entra en la primera versión y lo que queda para después. Antes era una lista plana.
- Me quedó abierta una decisión de seguridad: si cifrar o no la base, porque la tabla de libros guarda a quién le presté cada uno.

**Código**

- Creé el proyecto de Android Studio (`MiBookshelf/`) dentro de este repositorio.
- Agregué el plugin KSP y las dependencias de Room 2.8.4.
- Escribí la entidad `Libro`, el DAO `LibroDao`, la base `AppDatabase` con patrón Singleton y el `LibroViewModel`.
- Armé la pantalla principal en Compose: formulario de alta, lista con `LazyColumn` y borrado.
- El selector de orden (autor, título, fecha en que lo agregué) guarda la elección en `SharedPreferences`, así que la app abre como la dejé. Cada criterio es una consulta distinta en el DAO, el orden lo hace SQLite y no yo recorriendo la lista.
- Bajé a mano las versiones de `core-ktx` (1.19.0 a 1.18.0) y `lifecycle` (2.11.0 a 2.10.0). Las que puso Android Studio por defecto exigen compilar contra API 37 y el proyecto compila contra 36, así que el build fallaba antes de arrancar. Quedaron en las mismas versiones del ejemplo de clase.

Arreglos después de probarla en el emulador:

- Separé los campos del formulario. Sin separación, la etiqueta flotante del campo enfocado se montaba sobre el borde del campo de arriba.
- Año y Puntaje abren teclado numérico y descartan cualquier tecla que no sea un dígito, así el campo no acepta letras en primer lugar.
- El puntaje queda encerrado entre 0 y 5 con `coerceIn`. Antes podía guardar un libro con puntaje 47, y una escala sin tope no sirve para comparar un libro con otro.

Falta todavía: pantalla de detalle, editar, buscador, marcar prestado, colecciones y foto de portada.

---

## Cambios pasados

### Módulo 4 - Diseño

- Decidí hacer el diseño propio en vez de partir de una plantilla, para no perder el control sobre cómo se acomoda cada elemento de la lista de libros.
- Anoté como posibilidad lejana una búsqueda conversacional. Si algún día la hago, no puedo reutilizar la jerarquía visual del wireframe, hay que rediseñar la navegación desde cero.

### Módulo 3 - Wireframes

- Armé los wireframes de las cinco pantallas, que en el módulo 1 no existían: Mis libros, Un libro, Agregar libro, Colecciones y Configuración.
- Definí la navegación: el detalle se abre tocando un libro de la lista, el alta con el botón flotante `+`, y colecciones y configuración desde el menú de arriba a la derecha.
- Aparecieron en el wireframe de configuración dos opciones que no estaban en el borrador del módulo 1, vista de la lista y mostrar prestados primero, además del orden por defecto que ya tenía.

### Módulo 1 - Borrador inicial

- Primera versión del documento del proyecto, con la descripción, el problema, la plataforma tentativa, la funcionalidad y las pantallas descriptas en palabras.
- Descarté una idea previa que tenía dos roles de usuario distintos. La app la uso solo yo, así que no hay usuario y administrador sino pantalla de uso frecuente y pantalla de configuración.
- Dejé afuera desde el principio cualquier función social (amigos, compartir lecturas, clubes de lectura), que es justamente lo que me molestaba de las apps que había probado.

---

## Cambios futuros

Ordenados por cuándo pienso encararlos.

**Antes del módulo 7, con el código publicado acá**

- Entidad `Libro`, su DAO y la clase de base de datos de Room.
- Pantalla de lista de libros leyendo de la base.
- Pantalla de alta de un libro.
- Orden de la lista guardado en `SharedPreferences` y aplicado al abrir la app.
- Editar y borrar un libro desde la pantalla de detalle.
- Buscador por título y autor.
- Marcar un libro como prestado y como disponible.

**Si llego con los tiempos**

- Colecciones: entidades `Coleccion` y `LibroColeccion`, más las pantallas para crearlas y asignarlas.
- Foto de portada con la cámara, guardada en almacenamiento interno.
- Escaneo de ISBN con la cámara y autocompletado desde una API pública de libros, con carga manual como alternativa cuando no haya internet. Esta es la parte que va a usar Retrofit, que es el tema de las clases que siguen.

**Sin fecha**

- Cifrado de la base de datos.
- Alguna forma de exportar o respaldar la biblioteca, para no depender de un solo teléfono.
