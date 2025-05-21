package julliiua.lab6.server.commands;


import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.IdValidator;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;
import julliiua.lab6.server.utility.AskCommand;

/**
 * Команда 'add_if_min'. Добавляет элемент в коллекцию, если его продажи меньше минимального значения.
 */
public class AddIfMin extends AskCommand<NoArgValidator> {
    private final CollectionManager collectionManager;

    public AddIfMin(CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его продажи меньше минимального значения",  new NoArgValidator());
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse runInternal(MusicBand newband) {
        try {
            if (newband != null && newband.validate()) {
                double minSales = Double.MAX_VALUE;
                for (MusicBand band : collectionManager.getCollection()) {
                    double sales = band.getBestAlbum().getSales();
                    if (sales < minSales) {
                        minSales = sales;
                    }
                }

                if (collectionManager.getCollection().isEmpty() || newband.getBestAlbum().getSales() < minSales) {
                    collectionManager.add(newband);
                    return new ExecutionResponse(true, "Элемент успешно добавлен в коллекцию!");
                } else {
                    return new ExecutionResponse(false, "Элемент НЕ добавлен, так как его продажи не меньше минимальных.");
                }
            } else {
                return new ExecutionResponse(false, "Получен некорректный объект MusicBand");
            }
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка при выполнении команды: " + e.getMessage());
        }
    }
}