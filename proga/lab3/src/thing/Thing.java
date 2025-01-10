package thing;

public class Thing {
    private int count;
    private String thingName;

    public Thing(String thingName, int count) {
        this.thingName = thingName;
        this.count = count;
    }

    public String getThingName() {
        return thingName;
    }

    public int getCount() {
        return count;
    }

    public void addCount(int value) {
        if ((this.count + value) > 0) {
        }
        else{
            this.count = 0;
            throw new ZeroMeneyException("Недостаточно средств для покупки!!!");
        }

    }


}


class ZeroMeneyException extends RuntimeException {
    public ZeroMeneyException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Ошибка героя: " + super.getMessage();
    }
}