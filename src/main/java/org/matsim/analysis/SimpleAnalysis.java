package org.matsim.analysis;

import org.apache.commons.csv.CSVFormat;
import org.matsim.core.events.EventsUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleAnalysis {

    private static final String outFile ="C:\\Users\\phili\\IdeaProjects\\matsim-serengeti-park-hodenhagen\\scenarios\\serengeti-park-v1.0\\output\\output-serengeti-park-v1.0-run1\\serengeti-park-v1.0-run1.output_link_count.csv";
    public static void main(String[] args) {

        var handler = new SimplePersonEventHandler();
        var manager = EventsUtils.createEventsManager();
        manager.addHandler(handler);

        EventsUtils.readEvents(manager, "C:\\Users\\phili\\IdeaProjects\\matsim-serengeti-park-hodenhagen\\scenarios\\serengeti-park-v1.0\\output\\output-serengeti-park-v1.0-run1\\serengeti-park-v1.0-run1.output_events.xml.gz");
        var handler2 = new LinkEventHandler();
        manager.addHandler(handler2);
        var volumes = handler2.getVolumes();

        try(var writer = Files.newBufferedWriter(Paths.get(outFile)); var printer = CSVFormat.DEFAULT.withDelimiter(';').withHeader("Hour", "Value").print(writer)) {

            for(var volume : volumes.entrySet()){
                printer.printRecord(volume.getKey(), volume.getValue());
                printer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
