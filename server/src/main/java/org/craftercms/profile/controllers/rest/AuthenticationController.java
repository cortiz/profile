/*
 * Copyright (C) 2007-2014 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.profile.controllers.rest;

import org.craftercms.profile.api.PersistentLogin;
import org.craftercms.profile.api.Ticket;
import org.craftercms.profile.api.exceptions.ProfileException;
import org.craftercms.profile.api.services.AuthenticationService;
import org.craftercms.profile.exceptions.NoSuchPersistentLoginException;
import org.craftercms.profile.exceptions.NoSuchTicketException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.craftercms.profile.api.ProfileConstants.*;

/**
 * REST controller for the authentication service.
 *
 * @author avasquez
 */
@Controller
@RequestMapping(BASE_URL_AUTHENTICATION)

public class AuthenticationController {

    protected AuthenticationService authenticationService;

    public AuthenticationController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = URL_AUTH_AUTHENTICATE, method = RequestMethod.POST)
    @ResponseBody
    public Ticket authenticate(@RequestParam(PARAM_TENANT_NAME) String tenantName,
                               @RequestParam(PARAM_USERNAME) String username,
                               @RequestParam(PARAM_PASSWORD)
                                       String password) throws ProfileException {
        return authenticationService.authenticate(tenantName, username, password);
    }


    @RequestMapping(value = URL_AUTH_CREATE_TICKET, method = RequestMethod.POST)
    @ResponseBody
    public Ticket createTicket(
            @RequestParam(PARAM_PROFILE_ID) String profileId) throws ProfileException {
        return authenticationService.createTicket(profileId);
    }


    @RequestMapping(value = URL_AUTH_GET_TICKET, method = RequestMethod.GET)
    @ResponseBody
    public Ticket getTicket(
            @PathVariable(PATH_VAR_ID) String ticketId) throws ProfileException {
        Ticket ticket = authenticationService.getTicket(ticketId);
        if (ticket != null) {
            return ticket;
        } else {
            throw new NoSuchTicketException(ticketId);
        }
    }


    @RequestMapping(value = URL_AUTH_INVALIDATE_TICKET, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void invalidateTicket(
            @PathVariable(PATH_VAR_ID) String ticketId) throws ProfileException {
        authenticationService.invalidateTicket(ticketId);
    }


    @RequestMapping(value = URL_AUTH_CREATE_PERSISTENT_LOGIN, method = RequestMethod.POST)
    @ResponseBody
    public PersistentLogin createPersistentLogin(@RequestParam(PARAM_PROFILE_ID)
                                                         String profileId) throws ProfileException {
        return authenticationService.createPersistentLogin(profileId);
    }


    @RequestMapping(value = URL_AUTH_GET_PERSISTENT_LOGIN, method = RequestMethod.GET)
    @ResponseBody
    public PersistentLogin getPersistentLogin(
            @PathVariable(PATH_VAR_ID) String loginId) throws ProfileException {
        PersistentLogin login = authenticationService.getPersistentLogin(loginId);
        if (login != null) {
            return login;
        } else {
            throw new NoSuchPersistentLoginException(loginId);
        }
    }


    @RequestMapping(value = URL_AUTH_REFRESH_PERSISTENT_LOGIN_TOKEN, method = RequestMethod.POST)
    @ResponseBody
    public PersistentLogin refreshPersistentLoginToken(
            @PathVariable(PATH_VAR_ID) String loginId) throws ProfileException {
        return authenticationService.refreshPersistentLoginToken(loginId);
    }


    @RequestMapping(value = URL_AUTH_DELETE_PERSISTENT_LOGIN, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePersistentLogin(
            @PathVariable(PATH_VAR_ID) String loginId) throws ProfileException {
        authenticationService.deletePersistentLogin(loginId);
    }

}
