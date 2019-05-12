import React from 'react';
import { Prompt } from 'react-router-dom';
import {
  Container, Col, Form,
  FormGroup, Label, Input,
  Button, Row, Alert, Navbar, NavbarBrand
} from 'reactstrap';
import Constants from '../../config/Constants';
import store from 'store';
import get_token from '../../config/get_token';

class VmForm extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      os: '', 
      ram: '', 
      hdd: '', 
      cpuCores: '',
      error: false,
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    
  }

  handleSubmit() {
    const {os, ram, hdd, cpuCores} = this.state;
    let details = {
      'os': os,
      'ram': ram,
      'hdd': hdd,
      'cpuCores': cpuCores
    }
    const { history } = this.props;
    console.log(Constants.PROVISIONED_VMS_URL);
    let token = 'Bearer ' + get_token();
    console.log(token);
    fetch(Constants.PROVISIONED_VMS_URL, {
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
        'Authorization': token
      }),
      body: JSON.stringify(details)
    }).then((resp) => {
      return resp.text();
    })
      .then((resp) => {
        console.log(resp);
        let _json = JSON.parse(resp);
        console.log(_json);
        history.push('/provisionedvms');
      }).catch((error) => {
        this.setState({
          message: 'Error in User profile creation! ' + error
        });
        console.log("Error " + error);
      });
  }

  

  render() {
    const { os, ram, hdd, cpuCores, formChanged } = this.state;
    const { handleCancel, handleSubmit, submitText = 'Create' } = this.props;

    return (
    <Container className="App">
          <Col>
            <h2>Configure New VM</h2>
            <Form className="form">
              <Col>
                <FormGroup>
                  <Input
                    name="os"
                    placeholder="Operating Sstem"
                    onChange={v => this.setState({'os': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    name="ram"
                    placeholder="RAM"
                    onChange={v => this.setState({'ram': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    name="hdd"
                    placeholder="Hard Disk"
                    onChange={v => this.setState({'hdd': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Input
                    name="cpuCores"
                    placeholder="No. of CPU Cores"
                    onChange={v => this.setState({'cpuCores': v.target.value})}
                  />
                </FormGroup>
              </Col>
              <Button onClick={this.handleSubmit}>Submit</Button>
            </Form>
          </Col>
      </Container>

    );

    /* return (
      <Form onSubmit={this.handleSubmit}>
        <Prompt when={formChanged} message="Are you sure you wanna do that?" />
        <Form.Input
          label="Name"
          type="text"
          name="name"
          value={name}
          onChange={this.handleChange}
        />
        <Form.Input
          label="Email"
          type="email"
          name="email"
          value={email}
          onChange={this.handleChange}
        />
        <Form.Input
          label="Phone"
          type="tel"
          name="phone"
          value={phone}
          onChange={this.handleChange}
        />
        <Form.Input
          label="Address"
          type="text"
          name="address"
          value={address}
          onChange={this.handleChange}
        />
        <Form.Input
          label="City"
          type="text"
          name="city"
          value={city}
          onChange={this.handleChange}
        />
        <Form.Input
          label="Zip Code"
          type="text"
          name="zip"
          value={zip}
          onChange={this.handleChange}
        />
        <Form.Group>
          <Form.Button type="submit">{submitText}</Form.Button>
          <Form.Button onClick={handleCancel}>Cancel</Form.Button>
        </Form.Group>
      </Form>
    ); */
  }
}

export default VmForm;
