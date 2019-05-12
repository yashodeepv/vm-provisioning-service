import React from 'react';
import VmForm from './VmForm';
import Constants from '../../config/Constants';
import get_token from '../../config/get_token';

class ProvisionVM extends React.Component {
  constructor(props) {
    super(props);

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleCancel = this.handleCancel.bind(this);
  }

  handleSubmit(details) {
    
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
        history.push(`/provisionedvms`);
      }).catch((error) => {
        this.setState({
          message: 'Error in User profile creation! ' + error
        });
        console.log("Error " + error);
      });
  }

  handleCancel(e) {
    e.preventDefault();

    const { history } = this.props;

    history.push('/provisionedvms');
  }

  render() {
    return (
      

      <VmForm
        handleSubmit={this.handleSubmit}
        handleCancel={this.handleCancel}
        {...this.props}
      />
    );
  }
}

export default ProvisionVM;
