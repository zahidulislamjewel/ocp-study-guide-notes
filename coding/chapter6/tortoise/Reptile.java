package tortoise;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Reptile {
    protected void sleep() throws IOException {
    }

    protected void hide() {
    }

    protected void exitShell() throws FileNotFoundException {
    }
}

class GalapagosTortoise extends Reptile {
    @Override
    public void sleep() throws FileNotFoundException {
    }

    // Not allowed, cause hide() in parent doesn't declare any exception, 
    // so child hide() cannot declare any checked exception 
    // @Override
    // public void hide() throws FileNotFoundException {
    // }

    // This is allowed, can throw any unchecked exception
    @Override
    public void hide() throws RuntimeException {
    }

    // Not allowed, cause IOException is super class, not the same type or subclass
    // @Override
    // public void exitShell() throws IOException {
    // }

    // This is allowed, overridden method can throw any narrower type exception or none at all
    @Override
    public void exitShell() {

    }
}