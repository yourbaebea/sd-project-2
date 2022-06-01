package com.example.app;

import com.example.data.Team;
import com.example.data.Player;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("add")
public class RESTcontroller {
    @Autowired
    GameService gameService;
    @Autowired
    UserService userService;
    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    EventService eventService;


    @Value("${sports-api.key}")
    String api_key;

    String host = "https://v3.football.api-sports.io/";
    String api_host="v3.football.api-sports.io";

    @GetMapping(value = "player/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getPlayerAPI(@PathVariable("id") int id) throws UnirestException {
        //Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get(host + "players?id="+ id + "&season=2021")
                .header("x-rapidapi-host", api_host)
                .header("x-rapidapi-key", api_key)
                .asJson();


        if(response.getStatus()!=200){
            System.out.println("Something went wrong in get player api");
            return "error";
        }
        System.out.println("body "+ response.getBody().toString());
        System.out.println(response.getBody().getObject().getJSONArray("response"));
        JSONArray aux = response.getBody().getObject().getJSONArray("response");
        if(aux==null || aux.length()==0){
            System.out.println("empty");
            return "error";
        }

        JSONObject player= aux.getJSONObject(0).getJSONObject("player");
        String name= player.get("name").toString();
        String url= player.get("photo").toString();
        System.out.println(url);
        String birthdate = player.getJSONObject("birth").getString("date");
        System.out.println("name: "+ name+ " birthday "+ birthdate);
        //TODO
        this.playerService.addPlayer(new Player(id,name, birthdate, url));
        System.out.println("sucess in adding player "+ name);
        return "sucess in adding player "+ name;

    }

    @GetMapping(value = "team/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void getTeamAPI(@PathVariable("id") int id) throws UnirestException {
        //Unirest.setTimeouts(0, 0);
        HttpResponse <JsonNode> response = Unirest.get(host + "teams?id="+ id)
                .header("x-rapidapi-host", api_host)
                .header("x-rapidapi-key", api_key)
                .asJson();


        if(response.getStatus()!=200){
            System.out.println("Something went wrong in get player api");
            return;
        }


        JSONObject team = response.getBody().getObject().getJSONArray("response").getJSONObject(0).getJSONObject("team");
        String name= team.get("name").toString();
        String url= team.get("logo").toString();
        //TODO

        //URL url = new URL(logo);
        //Image image = ImageIO.read(url);
        //TODO ACHO QUE NAO É PRECISO GUARDAR A IMAGEM APENAS O LOGO, PODEMOS USAR NO HTML O LINK NAO É PRECISO TER A IMAGEM

        System.out.println(name + " url: "+ url);

        this.teamService.addTeam(new Team(name, url));

    }



}

   /* @GetMapping(value = "users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsers()
    {
        return userService.getAllUsers();   //list all users
    }

    @GetMapping(value = "teams", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Team> getTeams()
    {
        return teamService.getAllTeams();   //list all teams
    }

    @GetMapping(value = "games", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Game> getGames()
    {
        return gameService.getAllGames();   //list all games
    }

    @GetMapping(value = "users/{id}", produces = {MediaType.APPLICATION_XML_VALUE})
    public User getUser(@PathVariable("id") int id) {
        Optional<User> op = userService.getUser(id);
        if (op.isEmpty())
            return null;
        return op.get();
    }

    @GetMapping(value = "team/{id}", produces = {MediaType.APPLICATION_XML_VALUE})
    public Team getTeam(@PathVariable("id") int id) {
        Optional<Team> op = teamService.getTeam(id);
        if (op.isEmpty())
            return null;
        return op.get();
    }

    @PostMapping(value = "user", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addUsers(@RequestBody User user) {
        userService.addUser(user);
    }

    @PostMapping(value = "player", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addPlayers(@RequestBody Player player) {
        playerService.addPlayer(player);
    }

    @PostMapping(value = "game", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addGame(@RequestBody Game game) {
        gameService.addGame(game);
    }

    @PostMapping(value = "team", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addTeam(@RequestBody Team team) {
        teamService.addTeam(team);
    }

   *//* @PutMapping(value = "professors/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void addProfessor(@PathVariable("id") int id, @RequestBody Professor p) {
        Optional<Professor> op = profService.getProfessor(id);
        if (!op.isEmpty()) {
            Professor p1 = op.get();
            p1.setName(p.getName());
            p1.setOffice(p.getOffice());
            profService.addProfessor(p1);
        }
    }

    @PutMapping(value = "students/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void addStudent(@PathVariable("id") int id, @RequestBody Student s) {
        System.out.println("PUT called");
        Optional<Student> op = studentService.getStudent(id);
        if (!op.isEmpty()) {
            Student s1 = op.get();
            s1.setName(s.getName());
            s1.setAge(s.getAge());
            s1.setTelephone(s.getTelephone());
            studentService.addStudent(op.get());
        }
    }*//*
}
*/