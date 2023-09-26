# Task-web-application

Aplikacja służy do zarządzania zadaniami (tasks). Pozwala użytkownikom na tworzenie, edytowanie, usuwanie i pobieranie zadań. Aplikacja została zaimplementowana przy użyciu technologii Spring Framework w języku Java.

#
1. Baza danych:
   
	Aplikacja korzysta z relacyjnej bazy danych, która przechowuje informacje o zadaniach. Wykorzystuje JPA (Java Persistence API) oraz Spring Data JPA do komunikacji. Baza ta przechowuje zadania w tabeli o nazwie "tasks", z kolumnami "id", "name" (tytuł zadania) i 		"description" (opis zadania).

#
2. Biblioteki i technologie:
	Aplikacja wykorzystuje wiele bibliotek i technologii z ekosystemu Spring Framework:

*	org.springframework.http: Do obsługi klas HttpStatus oraz zwracania odpowiedzi z metodami HTTP.
*	org.springframework.web.bind.annotation: Do tworzenia kontrolerów oraz zarządzania punktami dostępu API.
*	org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler: Do obsługi wyjątków i zwracania odpowiednich odpowiedzi HTTP.
*	lombok: Do generowania automatycznie kodu związane z konstruktorami, getterami, setterami itp.
*	javax.transaction.Transactional: Do obsługi transakcji w bazie danych.
*	com.crud.tasks.mapper.TaskMapper: Prawdopodobnie do mapowania obiektów Task na obiekty DTO (Data Transfer Object) i odwrotnie.

#
3. Metody HTTP:

	Aplikacja udostępnia następujące metody HTTP poprzez kontroler TaskController:

*	GET /tasks: Pobieranie listy zadań (odpowiedź typu ResponseEntity<List<TaskDto>>).
*	GET /tasks/{taskId}: Pobieranie szczegółów jednego zadania (odpowiedź typu ResponseEntity<TaskDto>).
*	POST /tasks: Tworzenie nowego zadania (przyjmowanie TaskDto jako ciała żądania, odpowiedź typu ResponseEntity<Void>).
*	PUT /tasks: Aktualizacja istniejącego zadania (przyjmowanie TaskDto jako ciała żądania, odpowiedź typu ResponseEntity<TaskDto>).
*	DELETE /tasks/{taskId}: Usuwanie istniejącego zadania (odpowiedź typu ResponseEntity<Void>).
