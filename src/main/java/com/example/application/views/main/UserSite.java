package com.example.application.views.main;

import com.example.application.views.main.contents.UserSiteContent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * User settings site
 * @see Navigation
 */
@PageTitle("User")
@Route("user")
public class UserSite extends Navigation{
    public UserSite() {
        super("User");
        UserSiteContent userSiteContent = new UserSiteContent();
        setContent(userSiteContent);
    }
}
