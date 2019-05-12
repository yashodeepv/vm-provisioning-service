import React from 'react';
import { Switch, Route } from 'react-router-dom';
import Login from './Login';
import ManageVM from './ManageVM';

const App = () => (
  <div className="app-routes">
    <Switch>
      <Route path="/login" component={Login} />
      <Route path="/" component={ManageVM} />
    </Switch>
  </div>
);

export default App;
