package hello;

import main.java.riotapi.Request;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import constant.Region;
import dto.Champion.Champion;
import dto.Champion.ChampionList;
import dto.Game.Game;
import dto.Game.RawStats;
import dto.Game.RecentGames;
import dto.Summoner.Summoner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class GreetingController {
	private RiotApi api = new RiotApi("0b231558-1915-463b-8947-e4490b3e2b9f");	
	private ChampionList cl = new ChampionList();

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) throws RiotApiException {
	api.setRegion(Region.OCE);
	Map<String,Summoner> sum = null;

			sum = api.getSummonersByName(name);
		
		Summoner summoner = sum.values().iterator().next();
		name = name +" Level "+summoner.getSummonerLevel()+" ID: "+ summoner.getId();
		RecentGames rg = api.getRecentGames(summoner.getId());
		Set<Game> gs = rg.getGames();
		Iterator<Game> games = gs.iterator();
		Game g = games.next();
		RawStats rs = g.getStats();
		int kills = rs.getChampionsKilled();
		int deaths = rs.getCombatPlayerScore();
		int champID = g.getChampionId();
		Request r = new Request();
		String f ="NULL";
		f = r.execute("https://oce.api.pvp.net/api/lol/static-data/oce/v1.2/champion/"+champID+"?api_key=0b231558-1915-463b-8947-e4490b3e2b9f");
		model.addAttribute("champion1", f);
		model.addAttribute("kills1",kills);
		
		 g = games.next();
		 rs = g.getStats();
		 kills = rs.getChampionsKilled();
		 champID = g.getChampionId();
		f = r.execute("https://oce.api.pvp.net/api/lol/static-data/oce/v1.2/champion/"+champID+"?api_key=0b231558-1915-463b-8947-e4490b3e2b9f");
		model.addAttribute("champion2", f);
		model.addAttribute("kills2",kills);
		
		 g = games.next();
		 rs = g.getStats();
		 kills = rs.getChampionsKilled();
		 champID = g.getChampionId();
		f = r.execute("https://oce.api.pvp.net/api/lol/static-data/oce/v1.2/champion/"+champID+"?api_key=0b231558-1915-463b-8947-e4490b3e2b9f");
		model.addAttribute("champion3", f);
		model.addAttribute("kills3",kills);
		
        model.addAttribute("name", name);
        return "greeting";
    }

}
