package kancolle.fleet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kancolle.structure.Kanmusu;
import kancolle.structure.ShipType;
import kancolle.structure.Speed;

public class Fleets {
    private static List<Kanmusu> kanmusuList = new ArrayList<>();
    private static final Properties PROPERTIES;

    static{
        PROPERTIES = loadPropertiesFile();
    }

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

        // ヘッダ情報はいらないので除外する
        csvData.remove(0);

        List<Kanmusu> kanmusus = new ArrayList<Kanmusu>();
        csvData.forEach(line -> {
            kanmusus.add(new Kanmusu(line));
        });

        Logger.getGlobal().info("艦娘の数：" + kanmusus.size());

        kanmusuList.addAll(kanmusus);
    }

    private static Properties loadPropertiesFile(){
        Properties properties = new Properties();

        try{
            InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "\\Kanmusu-Naming.properties");
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            properties.load(reader);

            return properties;
        }catch(IOException e){
            Logger.getGlobal().severe(e.toString());
            return null;
        }
    }

    public static List<Kanmusu> getKanmusuList() {
        return kanmusuList;
    }

    public static List<String> filterKanmusuList(final ShipType shipType, final boolean isFastOnly,
            final int levelFilter, final String tag){

        Stream<Kanmusu> stream = Fleets.getKanmusuList().stream()
                .filter(kanmusu -> kanmusu.shipType() == shipType)
                .filter(kanmusu -> kanmusu.level() >= levelFilter)
                .sorted(Comparator.comparing(Kanmusu::initialName));

        if(isFastOnly){
            stream = stream.filter(kanmusu -> kanmusu.speed() == Speed.FAST);
        }
        if(!tag.equals("")){
            stream = stream.filter(kanmusu -> kanmusu.tag().equals(tag) || kanmusu.tag().equals(""));
        }

        return stream.map(Kanmusu::toString).collect(Collectors.toList());
    }

    public static String getInitialName(String key){
        String initial = PROPERTIES.getProperty(key);

        return Objects.isNull(initial) ? key : initial;
    }

    public static Kanmusu getKanmusuFromId(final int id){
        return kanmusuList.stream().filter(it -> it.id() == id)
                .findFirst().get();
    }

    public static void setTag(final int id, final String tag){
        Kanmusu kanmusu = getKanmusuFromId(id);

        kanmusu.setTag(tag);
        Logger.getGlobal().info(kanmusu + " sets tag : " + tag);
    }

    public static int getCount(final ShipType shipType){
        return (int) kanmusuList.stream().filter(kanmusu -> kanmusu.shipType() == shipType).count();
    }

    public static int getAverageLevel(final ShipType shipType){
        double average = kanmusuList.stream().filter(kanmusu -> kanmusu.shipType() == shipType)
                .mapToInt(Kanmusu::level).average().getAsDouble();

        return (int) Math.round(average);
    }

    public static int getMaxLevel(final ShipType shipType){
        return kanmusuList.stream().filter(kanmusu -> kanmusu.shipType() == shipType)
                .mapToInt(Kanmusu::level).max().getAsInt();
    }
}
