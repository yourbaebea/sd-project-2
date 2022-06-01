package com.example.demo;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import com.example.data.*;
import com.example.formdata.FormGame;
import com.example.formdata.FormInt;
import com.example.formdata.FormUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class DataController {

    @Autowired
    UserService userService;
    @Autowired
    TeamService teamService;
    @Autowired
    GameService gameService;
    @Autowired
    PlayerService playerService;
    @Autowired
    EventService eventService;

    public boolean sessionPermission(boolean adminonly, HttpServletRequest request){
        System.out.println ("perms:");
        HttpSession session= request.getSession();
        if(session.getAttribute("login")!=null) {
            boolean login= (boolean) session.getAttribute("login");
            if(!login) return false;
            System.out.println("logged in");
        }
        else return false;

        if(!adminonly) return true;

        if(session.getAttribute("admin")!=null) {
            boolean admin = (boolean) session.getAttribute("admin");
            System.out.println("admin");
            if(admin) return true;
        }

        System.out.println("not admin");
        return false;
    }

    @GetMapping("/")
    public String redirect(HttpServletRequest request) {
        return "redirect:/menuStart";
    }

    @GetMapping("/menuStart")
    public String menuStart(HttpServletRequest request) {
        return "menuStart";
    }

    @GetMapping("/menuUser")
    public String menuUser(Model model, HttpServletRequest request) {
        if(!sessionPermission(false, request)) return "errorSession";
        //m.addAttribute("person", new FormUser());
        Instant now = Instant.now();
        Timestamp currentTime = Timestamp.from(now);
        model.addAttribute("games", this.gameService.getCurrentFutureGames(currentTime));
        return "menuUser";
    }

    @GetMapping("/menuAdmin")
    public String menuAdmin(HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        return "menuAdmin";
    }

    @GetMapping("/loginUser")
    public String createLoginForm(Model model) {
        model.addAttribute("user", new FormUser());
        return "loginUser";
    }


    @PostMapping("/login")
    public String confirmLogin(@ModelAttribute FormUser user, Model m,HttpServletRequest request) {
        System.out.println("TRYING TO LOGIN WITH: "+ user.getName());
        User u = this.userService.checkLogin(user.getName(), user.getPassword());
        HttpSession session= request.getSession();
        if(u != null){
            session.setAttribute("login", true);
            session.setAttribute("user", u.getId());


            m.addAttribute("login", true);
            if(u.isAdmin()){
                //admin stuff
                session.setAttribute("admin", true);
                System.out.println("user logged in: " + user.getName() + user.getName() + u.isAdmin());
                session.setAttribute("admin", true);

                return "redirect:/menuAdmin";
            }
            else{
                //logged in user stuff
                session.setAttribute("admin", false);
                Instant now = Instant.now();
                Timestamp currentTime = Timestamp.from(now);
                m.addAttribute("games", this.gameService.getCurrentFutureGames(currentTime));
                return "redirect:/menuUser";
            }
        }
        else{
            String message = "Wrong username/password!";
            m.addAttribute("message", message);
            return "error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        if(!sessionPermission(false, request)) return "errorSession";
        request.getSession().setAttribute("login", false);
        request.getSession().setAttribute("admin", false);
        request.getSession().invalidate();
        return "menuStart";
    }

    @GetMapping("/registerUser")
    public String register(Model m,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        m.addAttribute("user", new User());
        return "registerUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        this.userService.addUser(user);
        return "menuAdmin";
    }

    @GetMapping("/listTeams")
    public String listTeams(Model model,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        model.addAttribute("searchint", new FormInt());
        model.addAttribute("teams", this.teamService.getAllTeams());
        return "listTeams";
    }

    @PostMapping("/createTeam")
    public String createTeam(@ModelAttribute FormInt searchint, Model m,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        return "redirect:/add/team/"+ searchint.getSearch();
    }

    @GetMapping("/listMembers")
    public String listMembers(@RequestParam(name="id", required=true) int id, Model m,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        Optional<Team> op = this.teamService.getTeam(id);

        if (op.isPresent()) {
            Team t=op.get();
            m.addAttribute("team", t);
            m.addAttribute("players", this.playerService.playersInTeam(id));
            return "listTeamMembers";
        }
        else {
            return "redirect:/listTeams";
        }
    }


    @GetMapping("/editTeam")
    public String editTeam(@RequestParam(name="id", required=true) int id, Model m,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        Optional<Team> op = this.teamService.getTeam(id);
        if (op.isPresent()) {
            m.addAttribute("team", op.get());
            return "createTeam";
        }
        else {
            return "redirect:/listTeams";
        }
    }

    @PostMapping("/saveTeam")
    public String saveTeam(@ModelAttribute Team team, Model model,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        System.out.println(team.getName());
        this.teamService.addTeam(team);
        model.addAttribute("teams", this.teamService.getAllTeams());
        return "redirect:/listTeams";
    }

    @GetMapping("/listPlayers")
    public String listPlayers(Model model,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        model.addAttribute("searchint", new FormInt());
        model.addAttribute("players", this.playerService.getAllPlayers());
        List<Player> list= this.playerService.getAllPlayers();
        for(Player p: list){
            System.out.println(p.getName());
        }
        return "listPlayers";
    }

    @PostMapping("/createPlayer")
    public String createPlayer(@ModelAttribute FormInt searchint, Model m,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        return "redirect:/add/player/"+ searchint.getSearch();
    }



    @GetMapping("/setTeamPlayer")
    public String setTeamPlayer(@RequestParam(name="id", required=true) int id, Model m,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        Optional<Player> op = this.playerService.getPlayer(id);
        if (op.isPresent()) {
            System.out.println("setteamplayer: "+ op.get().getName());
            if(op.get().getTeam()!=null){
                System.out.println("already have a team");
                return "redirect:/listPlayers";
            }
            m.addAttribute("player", op.get());
            m.addAttribute("allTeams", this.teamService.getAllTeams());
            return "addTeamToPlayer";
        }
        else {
            return "redirect:/listPlayers";
        }
    }


    @GetMapping("/editPlayer")
    public String editPlayer(@RequestParam(name="id", required=true) int id, Model m,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        Optional<Player> op = this.playerService.getPlayer(id);
        if (op.isPresent()) {
            m.addAttribute("player", op.get());
            m.addAttribute("allTeams", this.teamService.getAllTeams());
            return "addTeamToPlayer";
        }
        else {
            return "redirect:/listTeams";
        }
    }

    @PostMapping("/savePlayer")
    public String savePlayer(@ModelAttribute Player player, Model model,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        System.out.println("save player" + player.getName());
        this.playerService.addPlayer(player);
        //add player to team player list

        return "redirect:/listPlayers";
    }

    @GetMapping("/listGames")
    public String listGames(Model model,HttpServletRequest request) {
        model.addAttribute("games", this.gameService.getAllGames());
        if(!sessionPermission(false, request)) return "redirect:/listGamesNoLogin";

        return "listGames";
    }

    @GetMapping("/listGamesNoLogin")
    public String listGamesNoLogin(Model model) {
        model.addAttribute("games", this.gameService.getCurrentGames());
        return "listGamesNoLogin";
    }

    @GetMapping("/seeGameEvents")
    public String seeGameEvents(@RequestParam(name="id", required=true) int id, Model model) {
        List<Event> l = this.eventService.eventsInGame(id);
        Optional game = this.gameService.getGame(id);
        model.addAttribute("events", l);
        if (game.isPresent()) {
            model.addAttribute("game", game.get());
        }
        return "seeGameEvents";
    }

    @GetMapping("/createGame")
    public String createGame(Model m,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        m.addAttribute("formgame", new FormGame());
        m.addAttribute("allTeams", this.teamService.getAllTeams());
        return "createGame";
    }

    @GetMapping("/editGame")
    public String editGame(@RequestParam(name="id", required=true) int id, Model m,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        Optional<Game> op = this.gameService.getGame(id);
        if(op.isPresent()) {
            FormGame g = new FormGame();
            g.toForm(op.get());

            m.addAttribute("formgame", g);
            m.addAttribute("allTeams", this.teamService.getAllTeams());
            return "createGame";
        }
        return "redirect:/listGames";

    }

    @PostMapping("/saveGame")
    public String saveGame(@ModelAttribute FormGame formgame, Model model,HttpServletRequest request) {
        if(!sessionPermission(true, request)) return "errorSession";
        this.gameService.addGame(formgame.toGame());
        model.addAttribute("games", this.gameService.getAllGames());
        return "listGames";
    }
    @GetMapping("/addEvent")
    public String addEvent(@ModelAttribute Event event, Model m, HttpServletRequest request) {
        if(!sessionPermission(false, request)) return "errorSession";
        Instant now = Instant.now();
        Timestamp currentTime = Timestamp.from(now);
        event.setTime(currentTime);
        if (event.getType() == 1){
            m.addAttribute("event", event);
            return "saveTimeEvent";
        }
        else if (event.getType() == 4){
            if (event.getGame().isOver() || event.getGame().isInterrupted()) {
                String message = "This game is either paused or over!";
                m.addAttribute("message", message);
                return "error";}
            m.addAttribute("event", event);
            List<Player> ls = this.playerService.playersInGame(event.getGame().getId());
            m.addAttribute("players", ls);
            return "saveCardEvent";
        }
        else if (event.getType() == 3){
            if (event.getGame().isOver() || event.getGame().isInterrupted()) {
                String message = "This game is either paused or over!";
                m.addAttribute("message", message);
                return "error";}
            m.addAttribute("event", event);
            List<Team> ls = this.teamService.teamsInGame(event.getGame().getId());
            m.addAttribute("teams", ls);
            return "saveGoalTeam";
        }
        else{
            String message = "Invalid event type!";
            m.addAttribute("message", message);
            return "error";
        }

    }

    @GetMapping("/createEvent")
    public String createEvent(@RequestParam(name="id", required=true) int id, Model m, HttpServletRequest request) {
        if(!sessionPermission(false, request)) return "errorSession";
        Optional <Game> game = this.gameService.getGame(id);
        if (game.isPresent()){
            Game gameaux = game.get();
            Event event = new Event();
            event.setGame(gameaux);
            System.out.println(event.getGame().getId());
            m.addAttribute("event", event);
            return "addEvent";
        }
        String message = "Invalid game id!";
        m.addAttribute("message", message);
        return "error";
    }


    public void endGame(Event event){
        Game g= event.getGame();
        g.setOver(true);
        int r1= g.getResult1();
        int r2= g.getResult2();

        if(r1>r2){
            g.getTeam1().addWins();
            g.getTeam2().addLoses();
        }
        else{
            if(r1<r2){
                g.getTeam2().addWins();
                g.getTeam1().addLoses();
            }
            else{
                g.getTeam1().addDraws();
                g.getTeam2().addDraws();
            }

        }

        this.teamService.saveTeam(g.getTeam1());
        this.teamService.saveTeam(g.getTeam2());
        this.gameService.saveGame(g);

    }

    @PostMapping("/saveEvent")
    public String saveEvent(@ModelAttribute Event event, Model m, HttpServletRequest request) {
        if(!sessionPermission(false, request)) return "errorSession";
        System.out.println("Tipo de evento: "+ event.getType()+ "\nJogo: " + event.getGame().getId() + "\nStart: " + event.getGame().isStart() + "\nOver: " + event.getGame().isOver() + "\nInterrupted: " + event.getGame().isInterrupted()+ "\n\n" );
        if (event.getGame().isOver() && (event.getType() == 1 || event.getType() == 2 || event.getType() == 3 || event.getType() == 4 || event.getType() == 5 || event.getType() == 6 || event.getType() == 7)){ //jogo acabou e recebo qualquer comando (1,2,3,4,5,6,7)
            System.out.println("entrei no 1");
            String message = "This game is over!";
            m.addAttribute("message", message);
            return "error";
        }
        else if (!event.getGame().isStart() && event.getType() == 1){ //jogo nao começou e recebi um startgame
            event.getGame().setStart(true);
            System.out.println("entrei no 2");
        }
        else if (!event.getGame().isStart() && event.getType() != 1){ //jogo nao começou e recebi um comando diferente de startgame
            System.out.println("entrei no 3");
            String message = "Game has not started yet!";
            m.addAttribute("message", message);
            return "error";
        }
        else if (!event.getGame().isOver() && event.getType() == 2) { //jogo nao acabou e recebo endgame
            System.out.println("entrei no 4");
            endGame(event);
        }
        else if (event.getGame().isStart() && event.getType() == 1){ //jogo começou e recebi outro startgame
            System.out.println("entrei no 5");
            String message = "Game has already started!";
            m.addAttribute("message", message);
            return "error";
        }
        else if (!event.getGame().isInterrupted() && event.getType() == 6){ //jogo nao esta interrompido e recebi interrupt
            System.out.println("entrei no 6");
            event.getGame().setInterrupted(true);
        }
        else if (!event.getGame().isInterrupted() && event.getType() == 7){ //jogo nao esta interrompido e recebi resume
            System.out.println("entrei no 7");
            String message = "Game is not paused!";
            m.addAttribute("message", message);
            return "error";
        }
        else if (event.getGame().isInterrupted() && (event.getType() != 7 && event.getType() != 2)){ //jogo esta interrompido e recebi comando diferente de resume ou over
            System.out.println("entrei no 8");
            String message = "Game is paused!";
            m.addAttribute("message", message);
            return "error";
        }
        else if (event.getGame().isInterrupted() && event.getType() == 7){ //jogo esta interrompido e recebi resume
            System.out.println("entrei no 9");
            event.getGame().setInterrupted(false);
        }
        else if(event.getType() == 3){
            System.out.println("entrei no 10");
            event.getPlayer().addGoals();
            playerService.addPlayer(event.getPlayer());
            if (event.getTeam().getId() == event.getGame().getTeam1().getId()) gameService.updateScoreTeam1(event.getGame().getId());
            else gameService.updateScoreTeam2(event.getGame().getId());



            if (event.getTeam().getId() == event.getGame().getTeam1().getId()) gameService.updateScoreTeam1(event.getGame().getId());
            else gameService.updateScoreTeam2(event.getGame().getId());
        }
        System.out.println("nao entrei em nenhum");
        this.eventService.addEvent(event);
        Instant now = Instant.now();
        Timestamp currentTime = Timestamp.from(now);
        m.addAttribute("games", this.gameService.getCurrentFutureGames(currentTime));
        return "redirect:/menuUser";
    }
    @GetMapping("/saveEventCard")
    public String saveEventCard(@ModelAttribute Event event, Model model, HttpServletRequest request) {
        if(!sessionPermission(false, request)) return "errorSession";
        System.out.println(event.getType() + " " + event.getGame());
        List<Player> ls = this.playerService.playersInGame(event.getGame().getId());
        model.addAttribute("players", ls);
        return "saveCardEvent";
    }
    @GetMapping("/saveGoalTeam")
    public String saveGoalTeam(@ModelAttribute Event event, Model model, HttpServletRequest request) {
        if(!sessionPermission(false, request)) return "errorSession";
        System.out.println(event.getType() + " " + event.getGame());
        List<Team> ls = this.teamService.teamsInGame(event.getGame().getId());
        model.addAttribute("teams", ls);
        return "saveGoalTeam";
    }
    @GetMapping("/saveGoalPlayer")
    public String saveGoalPlayer(@ModelAttribute Event event, Model model, HttpServletRequest request) {
        if(!sessionPermission(false, request)) return "errorSession";
        List<Player> ls = this.playerService.playersInTeam(event.getTeam().getId());
        model.addAttribute("players", ls);
        return "saveGoalPlayer";
    }

    @GetMapping("/seeStatistics")
    public String seeStatistics(Model model) {
        List<Team> teams = this.teamService.getAllTeams();
        int maxGoals = this.playerService.maxGoals();
        List<Player> players = this.playerService.playerMostGoals(maxGoals);
        model.addAttribute("players", players);
        model.addAttribute("teams", teams);
        return "seeStatistics";
    }

}

