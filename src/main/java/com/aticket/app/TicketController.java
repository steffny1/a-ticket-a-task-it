// TicketController class

package com.aticket.app;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

    private static final Path TICKET_DIRECTORY = Paths.get("tickets");

    // Methods
    @RequestMapping("/ticket/put")
    public String putTicket(
            @RequestParam int id,
            @RequestParam String title,
            @RequestParam String type,
            @RequestParam int priority,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer error,
            @RequestParam(required = false) String task) throws IOException {

        Ticket ticket;

        // Construct a new instance of either BugTicket or TaskTicket
        if ("bug".equalsIgnoreCase(type)) {
            BugTicket bugTicket = new BugTicket();

            bugTicket.setDescription(
                    description == null ? "" : description
            );

            bugTicket.setError(
                    error == null ? 0 : error
            );

            ticket = bugTicket;

        } else if ("task".equalsIgnoreCase(type)) {
            TaskTicket taskTicket = new TaskTicket();

            taskTicket.setTask(
                    task == null ? "" : task
            );

            ticket = taskTicket;

        } else {
            throw new IllegalArgumentException(
                    "Invalid ticket type. Type must be bug or task."
            );
        }

        ticket.setId(id);
        ticket.setTitle(title);
        ticket.setPriority(priority);
        ticket.setStatus("Open");

        Files.createDirectories(TICKET_DIRECTORY);

        String fileName = id + ".txt";
        Path filePath = TICKET_DIRECTORY.resolve(fileName);

        Files.writeString(filePath, ticket.toString());

        return fileName;
    }

    @RequestMapping("/ticket/get")
    public Ticket getTicket(
            @RequestParam int id) throws IOException {

        Path filePath = TICKET_DIRECTORY.resolve(id + ".txt");

        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException(
                    "Ticket file " + id + ".txt was not found."
            );
        }

        String fileContents = Files.readString(filePath);

        Properties properties = new Properties();
        properties.load(new StringReader(fileContents));

        String type = properties.getProperty("type");

        Ticket ticket;

        if ("bug".equalsIgnoreCase(type)) {
            BugTicket bugTicket = new BugTicket();

            bugTicket.setDescription(
                    properties.getProperty("description", "")
            );

            bugTicket.setError(
                    Integer.parseInt(
                            properties.getProperty("error", "0")
                    )
            );

            ticket = bugTicket;

        } else if ("task".equalsIgnoreCase(type)) {
            TaskTicket taskTicket = new TaskTicket();

            taskTicket.setTask(
                    properties.getProperty("task", "")
            );

            ticket = taskTicket;

        } else {
            throw new IllegalArgumentException(
                    "The stored ticket type is invalid."
            );
        }

        ticket.setId(
                Integer.parseInt(properties.getProperty("id"))
        );

        ticket.setTitle(
                properties.getProperty("title")
        );

        ticket.setPriority(
                Integer.parseInt(properties.getProperty("priority"))
        );

        ticket.setStatus(
                properties.getProperty("status")
        );

        // Return the created ticket object
        return ticket;
    }
}