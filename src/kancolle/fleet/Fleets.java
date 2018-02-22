package kancolle.fleet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import kancolle.structure.Kanmusu;

public class Fleets {
    private static List<Kanmusu> kanmusuList = new ArrayList<>();

    public static void loadMyKanmusuCSVData(final String uri) {
        List<String> csvData = null;
        try {
            csvData = Files.readAllLines(Paths.get(uri));
        } catch (IOException e) {
            Logger.getGlobal().severe(e.toString());
        }

        if (Objects.isNull(csvData)) {
            Logger.getGlobal().severe("csvファイルのロードに失敗したっぽい");
            return;
        }

        List<Kanmusu> kanmusus = new ArrayList<Kanmusu>();
        csvData.forEach(line -> {
            kanmusus.add(new Kanmusu(line));
        });

        Logger.getGlobal().info("艦娘の数：" + kanmusus.size());

        kanmusuList.addAll(kanmusus);
    }

    public static List<Kanmusu> getKanmusuList() {
        return kanmusuList;
    }
}
