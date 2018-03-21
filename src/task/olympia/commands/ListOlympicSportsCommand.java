package task.olympia.commands;

import task.constructs.program.CommandSignature;
import task.exceptions.InvalidCallOfCommandException;
import task.exceptions.ValidationException;
import task.olympia.OlympiaApplication;
import task.olympia.validation.OlympiaValidator;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;

public class ListOlympicSportsCommand implements IExecutableCommand {
    private OlympiaApplication app; //TODO Come up with a good data holding variant
    private CommandSignature commandSignature = new CommandSignature("list-olympic-sports");

    public ListOlympicSportsCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            // Check the passed command against the signature it should have
            OlympiaValidator.validateCommand(command, this.commandSignature);

            outputStream.append("OK");
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        }
        /* catch (RegistrationException exception) {
            throw new InvalidCallOfCommandException(exception.getMessage());
        } */
    }

    @Override
    public String getSlug() {
        return this.commandSignature.getSlug();
    }

    @Override
    public String[] getArgs() {
        return this.commandSignature.getArgNames();
    }

    @Override
    public String getArg(int index) {
        return this.commandSignature.getArgName(index);
    }
}