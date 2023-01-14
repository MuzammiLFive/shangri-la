import React from "react";
import {get} from "../../common/http";
import "./home.css";

interface CustomerDetails {
    customerId: string;
    role: string;
    address: string;
    propertyType: string;
    bedroomNum: number;
    balance: number;
}

interface UProps {
    customerDetails: CustomerDetails;
}

export class Home extends React.Component<any, UProps> {
    constructor(props: any) {
        super(props);
    }

    componentDidMount() {
        this.getUserDetails();
    }

    getUserDetails() {
        get("/customer/details")
            .then(response => response?.json() as unknown as CustomerDetails)
            .then(data => {
                this.setState({
                    customerDetails: data
                });
            });
    }

    render() {
        const user = sessionStorage.getItem("user");
        return (
            <div>
                <div className="sub">Welcome {user} !</div>
                <div className="myInfo">My Info:</div>
                {this.state?.customerDetails ?
                    <div className="userInfo">
                        <div>Email:</div>
                        <div>{this.state.customerDetails.customerId}</div>
                        <div>Address:</div>
                        <div>{this.state.customerDetails.address}</div>
                        <div>Property Type:</div>
                        <div>{this.state.customerDetails.propertyType}</div>
                        <div>Number of Bedrooms:</div>
                        <div>{this.state.customerDetails.bedroomNum}</div>
                    </div>
                    : ""}
            </div>
        )
    }
}
