package command;

import manager.CollectionManager;
import models.Album;
import models.MusicBand;
import utility.Ask;
import utility.Console;
import utility.ExecutionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static utility.Ask.askAlbum;
import static utility.Ask.askBestAlbum;

/**
 * Команда 'filter_less_than_best_album'.
 * Выводит элементы коллекции, значение поля bestAlbum которых меньше заданного.
 */
public class FilterLessThanBestAlbumCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public FilterLessThanBestAlbumCommand(Console console, CollectionManager collectionManager) {
        super("filter_less_than_best_album", "вывести элементы, значение поля bestAlbum которых меньше заданного");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        //if(argument.isEmpty()) {
          //  return new ExecutionResponse(false, "Не задано значение для bestAlbum!");
        //}
        try {
            console.println("Введите информацию о bestAlbum (название):");
            Album sortAlbum = askBestAlbum(console);
            if (sortAlbum != null && sortAlbum.validate()) {
                PriorityQueue<MusicBand> bands = collectionManager.getCollection();
                if (bands.isEmpty()) {
                    return new ExecutionResponse(true, "Коллекция пуста => нет элементов для сравнения");
                }
                // проверка на сущевствование
                boolean albumFound = false;
                for (MusicBand band : collectionManager.getCollection()) {
                    if (band.getBestAlbum().equals(sortAlbum)) {
                        sortAlbum = band.getBestAlbum();
                        albumFound = true;
                        break;
                    }
                }
                if (albumFound) {
                    double sortAlbumSales = sortAlbum.getSales();

                    //сортировка
                    ArrayList<MusicBand> result = new ArrayList<>();
                    for (MusicBand band : collectionManager.getCollection()) {
                        double sales = band.getBestAlbum().getSales();
                        if (sales < sortAlbumSales) {
                            result.add(band);
                        }
                    }
                    result.sort(MusicBand::compareBySales);

                    if (!result.isEmpty()) {
                        for (MusicBand band : result) {
                            console.println(band);
                        }
                        return new ExecutionResponse(true, "Все элементы, у которых значение поля bestAlbum меньше заданного, выведены.");
                    } else {
                        return new ExecutionResponse(true, "Нет элементов с меньшими продажами, чем у заданного альбома.");
                    }
                }
                else {
                    return new ExecutionResponse(false, "Альбом с таким названием не найден в коллекции.");
                }
            } else
                return new ExecutionResponse(false, "Поля Album не валидны! Album не создан!");

        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "...");
        }

    }
}
