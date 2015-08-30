package org.merka.pentaho.ext.service;

import java.util.List;

import org.pentaho.platform.api.engine.security.userroledao.AlreadyExistsException;
import org.pentaho.platform.api.engine.security.userroledao.IPentahoRole;
import org.pentaho.platform.api.engine.security.userroledao.IPentahoUser;
import org.pentaho.platform.api.engine.security.userroledao.IUserRoleDao;
import org.pentaho.platform.api.engine.security.userroledao.NotFoundException;
import org.pentaho.platform.api.engine.security.userroledao.UncategorizedUserRoleDaoException;
import org.pentaho.platform.api.mt.ITenant;

public class MockUserRoleDao implements IUserRoleDao {

	@Override
	public IPentahoUser createUser(ITenant tenant, String username, String password, String description, String[] roles)
			throws AlreadyExistsException, UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPassword(ITenant tenant, String userName, String password)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUserDescription(ITenant tenant, String userName, String description)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(IPentahoUser user) throws NotFoundException, UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public IPentahoUser getUser(ITenant tenant, String name) throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPentahoUser> getUsers() throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPentahoUser> getUsers(ITenant tenant) throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPentahoUser> getUsers(ITenant tenant, boolean includeSubtenants)
			throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPentahoRole createRole(ITenant tenant, String roleName, String description, String[] memberUserNames)
			throws AlreadyExistsException, UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRoleDescription(ITenant tenant, String roleName, String description)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRole(IPentahoRole role) throws NotFoundException, UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public IPentahoRole getRole(ITenant tenant, String name) throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPentahoRole> getRoles() throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPentahoRole> getRoles(ITenant tenant) throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPentahoRole> getRoles(ITenant tenant, boolean includeSubtenants)
			throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRoleMembers(ITenant tenant, String roleName, String[] memberUserNames)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUserRoles(ITenant tenant, String userName, String[] roles)
			throws NotFoundException, UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IPentahoUser> getRoleMembers(ITenant tenant, String roleName) throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPentahoRole> getUserRoles(ITenant tenant, String userName) throws UncategorizedUserRoleDaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
