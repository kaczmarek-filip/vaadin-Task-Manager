package com.example.application.views.main;

import com.example.application.components.contents.TeamSiteContent;
import com.example.application.components.dialogs.CreateTeamDialog;
import com.example.application.components.elements.components.OnSaveReload;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.Route;

/**
 * Teams management site
 *
 * @see Navigation
 */
@Route("teams")
public class TeamsSite extends Navigation implements OnSaveReload {

    private Button createTeamButton = new Button("Create team");
    private TeamSiteContent teamSiteContent = new TeamSiteContent();

    public TeamsSite() {
        super("Teams");

        setContent(teamSiteContent);

        createTeamButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        createTeamButton.addClickListener(e -> {
            CreateTeamDialog createTeamDialog = new CreateTeamDialog();
            createTeamDialog.setParent(this);
            createTeamDialog.open();
        });

        addTopNavButton(createTeamButton);
    }

    @Override
    public void OnChangeReload() {
        remove(teamSiteContent);
        setContent(new TeamSiteContent());
    }
}
