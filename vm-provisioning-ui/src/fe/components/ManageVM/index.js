import React from 'react';
import store from 'store';
import { Route, Link, Switch, Redirect } from 'react-router-dom';
import isLoggedIn from '../../../config/is_logged_in';
import styles from './styles.css';
import {
  Button,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  Collapse
} from 'reactstrap';
import ProvisionedVMs from '../ProvisionedVMs';
import ProvisionVM from '../ProvisionVM';

const handleLogout = history => () => {
  store.remove('loggedIn');
  store.remove('token');
  history.push('/login');
};

const ManageVM = ({ history }) => {
  if (!isLoggedIn()) {
    return <Redirect to="/login" />;
  }

  return (
    <div>
      <Navbar color="light" light expand="md">
        <NavbarBrand href="/provisionedvms">VM Provisioning</NavbarBrand>
          <Nav className="ml-auto" navbar>
            <NavItem>
              <NavLink><Link class="nav-link" to="/provisionedvms">Provisioned VMs</Link></NavLink>
            </NavItem>
            <NavItem>
              <NavLink><Link class="nav-link" to="/provisionedvms/new">Provision New VM</Link></NavLink>
            </NavItem>
            <NavItem>
              <NavLink><Button class="nav-link" onClick={handleLogout(history)}>Log out</Button></NavLink>
            </NavItem>
          </Nav>        
      </Navbar>
      


      <div className={styles.mainBody}>
        <Switch>
        <Route path="/provisionedvms/new" component={ProvisionVM} />
          <Route path="/provisionedvms" component={ProvisionedVMs} />
        </Switch>
      </div>
    </div >
  );
};

export default ManageVM;
