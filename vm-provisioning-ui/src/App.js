import React from 'react';
import App from './fe/components/App';
import './App.css';
import { BrowserRouter as Router } from 'react-router-dom';

const AppWrapper = () => (
  <div className="app-routes">
    <Router>
      <App />
    </Router>
  </div>
);

export default AppWrapper;
