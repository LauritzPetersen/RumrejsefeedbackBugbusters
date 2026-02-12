package Service;

import Model.CriticalStatusException;
import Model.Spaceship;
import View.GameView;

import java.util.Random;

public class GameEvents {
    private Spaceship spaceship;
    private GameView gameView = new GameView();
    private Random random =  new Random();

    public GameEvents() {

    }

    public void startGame(){
        try{
            spaceship = gameView.promptSpaceShipCreation();
            eventStorm();
        } catch (CriticalStatusException e) {
            gameView.printMessage("\n GAMER OVER:");
        } finally {
            gameView.printLog(spaceship);
        }

    }

    public void checkCriticalStatus(){
        if (spaceship.getFuel() < 10){
            throw new CriticalStatusException("Brændstof er lavt!: " + spaceship.getFuel());
        }
        if (spaceship.getIntegrity() < 20){
            throw new CriticalStatusException("Integritet er lavt!:  " + spaceship.getIntegrity());
        }
    }

    public void eventStorm(){
        gameView.printMessage("\n----Event 1----\n" +
                                "1) Flyv igennem stormen (Høj risiko)\n" +
                                "2) Tag en omvej (-10 Brændstof lav risiko)\n");

        boolean validChoise = false;
        while (!validChoise) {
            try{
                int choice = gameView.readUserInput("Dit valg: ");
                int damage;

                switch (choice){
                    case 1->{
                        damage = random.nextInt(50)+1;
                        if(spaceship.getShieldLevel() > 0){
                            damage = damage / 2;
                            spaceship.addLog("Event 1: Valgt Storm, Skade = " + damage);
                        }
                        spaceship.takeDamage(damage);
                        gameView.printMessage("\nSkade taget: " + damage);
                        gameView.printStatus(spaceship);
                        validChoise =  true;
                    }
                    case 2 ->{
                        damage = random.nextInt(20)+1;
                        spaceship.burnFuel(10);
                        spaceship.takeDamage(damage);
                        gameView.printMessage("\nSkade taget: -" + damage + "\n" +
                                "Brændstof:   -10");
                        spaceship.addLog("Event 1: Valgt omvej, Skade = " + damage + " Brændstof: " + spaceship.getFuel());
                        gameView.printStatus(spaceship);
                        validChoise =  true;
                    }
                    default ->{
                        gameView.printMessage("Ikke gyldigt. Tryk venligst 1 eller 2.");
                    }
                }
            } catch (NumberFormatException e) {
                gameView.printMessage("Skriv venligst et tal.");
            } catch (IllegalArgumentException e) {
                gameView.printMessage("Skriv venligst et tal. Som enten er 1 eller 2");
            }
            checkCriticalStatus();

        }


    }






}
