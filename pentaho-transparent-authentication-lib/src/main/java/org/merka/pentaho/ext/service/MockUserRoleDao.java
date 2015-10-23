package org.merka.pentaho.ext.service;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.platform.api.engine.security.userroledao.AlreadyExistsException;
import org.pentaho.platform.api.engine.security.userroledao.IPentahoRole;
import org.pentaho.platform.api.engine.security.userroledao.IPentahoUser;
import org.pentaho.platform.api.engine.security.userroledao.IUserRoleDao;
import org.pentaho.platform.api.engine.security.userroledao.NotFoundException;
import org.pentaho.platform.api.engine.security.userroledao.UncategorizedUserRoleDaoException;
import org.pentaho.platform.api.mt.ITenant;
import org.pentaho.platform.security.userroledao.PentahoRole;
import org.pentaho.platform.security.userroledao.PentahoUser;

public class MockUserRoleDao implements IUserRoleDao {

	@Override
	public IPentahoUser createUser(ITenant tenant, String username, String password, String description, String[] roles)
			throws AlreadyExistsException, UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public void setPassword(ITenant tenant, String userName, String password)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		

	}

	@Override
	public void setUserDescription(ITenant tenant, String userName, String description)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		

	}

	@Override
	public void deleteUser(IPentahoUser user) throws NotFoundException, UncategorizedUserRoleDaoException {
		

	}

	@Override
	public IPentahoUser getUser(ITenant tenant, String name) throws UncategorizedUserRoleDaoException {
		return new PentahoUser(name);
	}

	@Override
	public List<IPentahoUser> getUsers() throws UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public List<IPentahoUser> getUsers(ITenant tenant) throws UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public List<IPentahoUser> getUsers(ITenant tenant, boolean includeSubtenants)
			throws UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public IPentahoRole createRole(ITenant tenant, String roleName, String description, String[] memberUserNames)
			throws AlreadyExistsException, UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public void setRoleDescription(ITenant tenant, String roleName, String description)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		

	}

	@Override
	public void deleteRole(IPentahoRole role) throws NotFoundException, UncategorizedUserRoleDaoException {
		

	}

	@Override
	public IPentahoRole getRole(ITenant tenant, String name) throws UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public List<IPentahoRole> getRoles() throws UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public List<IPentahoRole> getRoles(ITenant tenant) throws UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public List<IPentahoRole> getRoles(ITenant tenant, boolean includeSubtenants)
			throws UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public void setRoleMembers(ITenant tenant, String roleName, String[] memberUserNames)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		

	}

	@Override
	public void setUserRoles(ITenant tenant, String userName, String[] roles)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		

	}

	@Override
	public List<IPentahoUser> getRoleMembers(ITenant tenant, String roleName) throws UncategorizedUserRoleDaoException {
		
		return null;
	}

	@Override
	public List<IPentahoRole> getUserRoles(ITenant tenant, String userName) throws UncategorizedUserRoleDaoException {
		PentahoRole role = new PentahoRole("Administrator");
		ArrayList<IPentahoRole> roles = new ArrayList<>();
		roles.add(role);
		return roles;
	}

}
