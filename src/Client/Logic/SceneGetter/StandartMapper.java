package Client.Logic.SceneGetter;

import Shared.CommunicationLibrary;

import java.util.HashMap;
import java.util.Map;

public class StandartMapper {
    public static HashMap<String, Integer> getEmptyMap(){
      HashMap<String, Integer> scores = new HashMap<>();
        scores.put(CommunicationLibrary.KEYS_SUCCESSES_USA, 0);
        scores.put(CommunicationLibrary.KEYS_SUCCESSES_USSR, 0);
        scores.put(CommunicationLibrary.KEYS_KILLED_USA, 0);
        scores.put(CommunicationLibrary.KEYS_KILLED_USSR, 0);
        scores.put(CommunicationLibrary.KEYS_WASTED_USA, 0);
        scores.put(CommunicationLibrary.KEYS_WASTED_USSR,0);
        return scores;
    }

    public static HashMap<String, Integer> scoreReader(String request){
        HashMap<String, Integer> scores = new HashMap<>();
        char[] charRequest = request.toCharArray();
        int indexStartReading = request.indexOf('#');
        int valuesRead = 0;
        String cachedValue = "";
        while (valuesRead < 7){
            indexStartReading++;
            if (charRequest[indexStartReading] == '!'){
                valuesRead++;
                if (valuesRead == 1){
                    scores.put(CommunicationLibrary.KEYS_SUCCESSES_USA, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 2){
                    scores.put(CommunicationLibrary.KEYS_KILLED_USA, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 3){
                    scores.put(CommunicationLibrary.KEYS_WASTED_USA, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 4){
                    scores.put(CommunicationLibrary.KEYS_SUCCESSES_USSR, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 5){
                    scores.put(CommunicationLibrary.KEYS_KILLED_USSR, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 6){
                    scores.put(CommunicationLibrary.KEYS_WASTED_USSR, Integer.parseInt(cachedValue));
                    valuesRead++;
                }
                cachedValue = "";
            } else {
                cachedValue += charRequest[indexStartReading];
            }
        }
        return scores;
    }

    public static String scoreWinner(String request){
        Map<String, Integer> finalsocres =  scoreReader(request);
        int wastedUSA = finalsocres.get(CommunicationLibrary.KEYS_WASTED_USA ) + (finalsocres.get(CommunicationLibrary.KEYS_KILLED_USA) * 1000000);
        int wastedUSSR = finalsocres.get(CommunicationLibrary.KEYS_WASTED_USSR ) + (finalsocres.get(CommunicationLibrary.KEYS_KILLED_USSR) * 1000000);
        if (wastedUSA > wastedUSSR){
            return "USSR";
        } else if (wastedUSSR > wastedUSA) {
            return "USA";
        } else {
            return "NOBODY";
        }

    }


}
