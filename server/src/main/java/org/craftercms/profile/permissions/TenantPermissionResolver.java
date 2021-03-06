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
package org.craftercms.profile.permissions;

import org.craftercms.commons.security.exception.PermissionException;
import org.craftercms.commons.security.permissions.Permission;
import org.craftercms.commons.security.permissions.PermissionResolver;
import org.craftercms.profile.api.AccessToken;
import org.craftercms.profile.api.TenantPermission;

/**
 * {@link org.craftercms.commons.security.permissions.PermissionResolver} for tenants.
 *
 * @author avasquez
 */
public class TenantPermissionResolver implements PermissionResolver<AccessToken, String> {

    @Override
    public Permission getGlobalPermission(AccessToken token) throws IllegalArgumentException, PermissionException {
        return getPermission(token, TenantPermission.ANY_TENANT);
    }

    @Override
    public Permission getPermission(AccessToken token, String tenantName) throws PermissionException {
        for (TenantPermission permission : token.getTenantPermissions()) {
            String permittedTenant = permission.getTenant();

            if (permittedTenant.equals(TenantPermission.ANY_TENANT) || permittedTenant.equals(tenantName)) {
                return permission;
            }
        }

        return null;
    }

}
