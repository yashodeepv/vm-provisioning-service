import React from 'react';
import {
  Table

} from 'reactstrap';
import Constants from '../../config/Constants';
import get_token from '../../config/get_token';


class ProvisionedVMs extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      provisionedVms: [],
      token: '',
      message: ''
    }
  }

  componentDidMount() {
    this.getProvisionedVms();
  }

  componentWillReceiveProps({ location = {} }) {
    if (location.pathname === '/provisionedvms' && location.pathname !== this.props.location.pathname) {
      this.getProvisionedVms();
    }
  }

  getProvisionedVms() {
    console.log(Constants.PROVISIONED_VMS_URL);
    let token = 'Bearer ' + get_token();
    console.log(token);
    fetch(Constants.PROVISIONED_VMS_URL, {
      method: 'GET',
      headers: new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
        'Authorization': token
      })
    }).then((resp) => {
      return resp.text();
    })
      .then((resp) => {
        console.log(resp);
        let _json = JSON.parse(resp);
        console.log(_json);
        let rs = _json;
        this.setState({
          provisionedVms: rs
        });
      }).catch((error) => {
        this.setState({
          message: 'Error in User profile creation! ' + error
        });
        console.log("Error " + error);
      });
  }

  setPage(page) {
    return () => {
      this.setState({ page });
    };
  }

  decrementPage() {
    const { page } = this.state;

    this.setState({ page: page - 1 });
  }

  incrementPage() {
    const { page } = this.state;

    this.setState({ page: page + 1 });
  }

  handleDelete(userId) {
    const { users } = this.state;

    this.setState({
      users: users.filter(u => u.id !== userId),
    });
  }

  render() {
    const { provisionedVms } = this.state;

    return (
      <Table>
        <thead>
          <tr>
            <th>VM_ID</th>
            <th>Operating System</th>
            <th>Ram</th>
            <th>HDD</th>
            <th>CPU Cores</th>
            <th>Allocated Space</th>
          </tr>
        </thead>
        <tbody>
          {provisionedVms.map(provisionedVm => (
            <tr scope="row">
              <th>{provisionedVm.id}</th>
              <th>{provisionedVm.vmConfig.os}</th>
              <th>{provisionedVm.vmConfig.ram}</th>
              <th>{provisionedVm.vmConfig.hdd}</th>
              <th>{provisionedVm.vmConfig.cpuCores}</th>
              <th>{provisionedVm.allocatedSpace}</th>
            </tr>
          ))}
        </tbody>
      </Table>
    );
  }
}

export default ProvisionedVMs;
