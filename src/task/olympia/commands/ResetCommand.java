package task.olympia.commands;

import task.constructs.program.CommandSignature;
import task.exceptions.AuthException;
import task.exceptions.InvalidCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.userinterface.auth.Permission;
import task.userinterface.validation.InputValidator;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;

import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

public class ResetCommand implements IExecutableCommand, IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature("reset");

    public ResetCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public Permission[] getPermissionFlags() {
        return this.requiredPermissions;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            this.checkPermissions(this.app.getSession());

            this.app.getInputValidator().validateCommand(command, this.commandSignature);

            this.app.reset();

            outputStream.append("OK");
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        } catch (AuthException authException) {
            throw new InvalidCallOfCommandException(authException.getMessage());
        }
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
