import React from 'react';
import store from 'store';
import { Redirect } from 'react-router-dom';
import isLoggedIn from '../../../config/is_logged_in'
import styles from './styles.css';
import Constants from '../../../config/Constants'
import {
  Container, Col, Form,
  FormGroup, Label, Input,
  Button, Row, Alert, Navbar, NavbarBrand
} from 'reactstrap';

class Login extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: '',
      name: '',
      designation: '',
      country: '',
      emailAddress: '',
      mobileNumber: '',
      message: '',
      error: false,
    };

    this.handleChange = this.handleChange.bind(this);
    this.onSignin = this.onSignin.bind(this);
    this.onSignup = this.onSignup.bind(this);
    this.showMessage = this.showMessage.bind(this);
  }

  onSignup(e) {
    e.preventDefault();

    const { username, password, name, designation, country, emailAddress, mobileNumber } = this.state;
    const { history } = this.props;

    var details = {
      'username': username,
      'password': password,
      'name': name,
      'emailAddress': emailAddress,
      'mobileNumber': mobileNumber,
      'roles' : [
        'USER'
      ],
      'active': true
    };
    console.log(Constants.SIGNUP_SERVICE_URL);
    fetch(Constants.SIGNUP_SERVICE_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8'
      },
      body: JSON.stringify(details)
    }).then((resp) => {
      return resp.text();
    })
      .then((resp) => {
        console.log(resp);
        let _json = JSON.parse(resp);
        console.log(_json);
        let rs = _json;
        this.setState({
          message: rs.status ? rs.message : 'User Profile created successfully!'
        });        
      }).catch((error) => {
        this.setState({
          message: 'Error in User profile creation! '+error
        });
        console.log("Error " + error);
      }); 

  }

  onSignin(e) {
    e.preventDefault();

    const { username, password } = this.state;
    const { history } = this.props;

    var details = {
      'username': username,
      'password': password
    };
    var formBody = [];
    for (var property in details) {
      var encodedKey = encodeURIComponent(property);
      var encodedValue = encodeURIComponent(details[property]);
      formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");
    console.log(formBody);
    console.log(Constants.LOGIN_SERVICE_URL);

    fetch(Constants.LOGIN_SERVICE_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8'
      },
      body: JSON.stringify(details)
    }).then((resp) => {
      return resp.text();
    })
      .then((resp) => {
        console.log(resp);
        let _json = JSON.parse(resp);
        console.log(_json);
        let rs = _json;
        store.set('token', rs.token);
        store.set('loggedIn', true);
        history.push('/provisionedvms');
      }).catch((error) => {
        this.setState({ error: true });
        console.log("Error " + error);
      });

  }

  handleChange(e, { name, value }) {
    this.setState({ [name]: value });
  }

  showMessage() {
    return this.state.message ? (<Alert color="primary">
        {this.state.message}
      </Alert>) : null;
  }

  render() {
    const { error } = this.state;

    if (isLoggedIn()) {
      return <Redirect to="/provisionedvms" />;
    }

    return (<div>
      <nav class="navbar navbar-expand-sm bg-light navbar-light">
          <div class="navbar-header">
            <a class="navbar-brand" href="#">VM Provisioning</a>
          </div>
      </nav>
      <Container className="App">
        <Row>
          <Col>
            <h2>Sign Up</h2>
            {this.showMessage()}
            <Form className="form">
              <Col>
                <FormGroup>
                  <Input
                    name="name"
                    placeholder="Name"
                    onChange={v => this.setState({'name': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    name="designation"
                    placeholder="Designation"
                    onChange={v => this.setState({'designation': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    name="country"
                    placeholder="Country"
                    onChange={v => this.setState({'country': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    name="emailAddress"
                    placeholder="Email Id"
                    onChange={v => this.setState({'emailAddress': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    name="mobileNumber"
                    placeholder="Mobile Number"
                    onChange={v => this.setState({'mobileNumber': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    name="username"
                    id="username"
                    placeholder="Username"
                    onChange={v => this.setState({'username': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    type="password"
                    name="password"
                    placeholder="********"
                    onChange={v => this.setState({'password': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Button onClick={this.onSignup}>Submit</Button>
            </Form>
          </Col>
          <Col>
            <h2>Sign In</h2>
            <Form className="form" >
              <Col>
                <FormGroup>
                  <Input
                    name="username"
                    placeholder="Username"
                    onChange={v => this.setState({'username': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    type="password"
                    name="password"
                    id="examplePassword"
                    placeholder="********"
                    onChange={v => this.setState({'password': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Button onClick={this.onSignin}>Sign In</Button>
            </Form>
          </Col>
        </Row>
      </Container>
      </div>
    );
  }
}

export default Login;
