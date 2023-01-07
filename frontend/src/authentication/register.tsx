import React from "react";
import {ErrorTag} from "../error-tag/error-tag";
import './register.css';
import {preloginPost} from "../common/http";

const PropertyTypes = [
    "detached",
    "semi-detached",
    "terraced",
    "flat",
    "cottage",
    "bungalow",
    "mansion"
];

interface RProps {
    emailError?: string;
    passwordError?: string;
    addressError?: string;
    propertyTypeError?: string;
    bedroomNumError?: string;
    voucherError?: string;
    responseError?: string;
}

export class Register extends React.Component<any, RProps> {
    constructor(props: any) {
        super(props);
        this.state = {
            emailError: undefined,
            passwordError: undefined,
            addressError: undefined,
            propertyTypeError: undefined,
            bedroomNumError: undefined,
            voucherError: undefined,
            responseError: undefined
        }
    }

    validateEmail(email: string): boolean {
        const re = /\S+@\S+\.\S+/;
        return re.test(email);
    }

    register() {
        const email = (document.getElementById("email") as HTMLInputElement).value;
        const pass1 = (document.getElementById("password1") as HTMLInputElement).value;
        const pass2 = (document.getElementById("password2") as HTMLInputElement).value;
        const address = (document.getElementById("address") as HTMLInputElement).value;
        const property: PropertyKey = (document.getElementById("propertyType") as HTMLSelectElement).value;
        const voucher = (document.getElementById("voucher") as HTMLInputElement).value;
        const bedroomNum = (document.getElementById("bedroomNum") as HTMLInputElement).value;

        if (email === "" || !this.validateEmail(email)) {
            this.setState({emailError: "Invalid Email"});
            return;
        }
        if (pass1 === "" || pass2 === "") {
            this.setState({passwordError: "Password cannot be empty"});
            return;
        }
        if (pass1 !== pass2) {
            this.setState({passwordError: "Passwords do not match."});
            return;
        }
        if (address === "") {
            this.setState({addressError: "Address cannot be empty"});
            return;
        }
        if (property === "") {
            this.setState({propertyTypeError: "Property type cannot be empty"});
            return;
        }
        if (bedroomNum === "") {
            this.setState({bedroomNumError: "Bedroom count cannot be empty"});
            return;
        }
        if (voucher === "") {
            this.setState({voucherError: "Invalid voucher"});
            return;
        }

        preloginPost("/api/register", {
            "email": email,
            "password1": pass1,
            "password2": pass2,
            "address": address,
            "propertyType": property,
            "bedroomNum": bedroomNum,
            "voucher": voucher
        }).then(response => {
            if (response?.ok) {
                alert("Registered!");
                window.location.reload();
            }
            return response?.json();
        }).then(json => {
            const message = json["message"];
            if (message)
                this.setState({responseError: message})
        }).catch(error => {
            console.log(error);
            alert(error);
        });
    }

    reset() {
        this.setState({
            emailError: undefined,
            passwordError: undefined,
            addressError: undefined,
            propertyTypeError: undefined,
            bedroomNumError: undefined,
            voucherError: undefined,
            responseError: undefined
        });
    }

    render() {
        return (
            <div className="registration">
                {!!this.state.responseError ? <ErrorTag id="responseError" error={this.state.responseError}/> : ""}
                <div className="cell">
                    <div>Email id:</div>
                    <div><input id="email" type="email" onClick={() => this.reset()}/></div>
                </div>
                {!!this.state.emailError ? <ErrorTag id="usernameError" error={this.state.emailError}/> : ""}
                <div className="cell">
                    <div>Password:</div>
                    <div><input id="password1" type="password"
                                onClick={() => this.reset()}/></div>
                </div>
                <div className="cell">
                    <div>Confirm Password:</div>
                    <div><input id="password2" type="password"
                                onClick={() => this.reset()}/></div>
                </div>
                {!!this.state.passwordError ? <ErrorTag id="passwordError" error={this.state.passwordError}/> : ""}
                <div className="cell">
                    <div>Address:</div>
                    <div><input id="address" type="text" required onClick={() => this.reset()}/></div>
                </div>
                {!!this.state.addressError ? <ErrorTag id="addressError" error={this.state.addressError}/> : ""}
                <div className="cell">
                    <div>Property Type:</div>
                    <div>
                        <select id="propertyType" onClick={() => this.reset()}>
                            <option value="">Select type</option>
                            {PropertyTypes.map(property => <option value={property}>{property}</option>)}
                        </select>
                    </div>
                </div>
                {!!this.state.propertyTypeError ?
                    <ErrorTag id="propertyError" error={this.state.propertyTypeError}/> : ""}
                <div className="cell">
                    <div>Number of Bedrooms:</div>
                    <div><input id="bedroomNum" type="number" min="0" max="7" onClick={() => this.reset()}/>
                    </div>
                </div>
                {!!this.state.bedroomNumError ? <ErrorTag id="bedroomError" error={this.state.bedroomNumError}/> : ""}
                <div className="cell">
                    <div>Voucher code:</div>
                    <div><input id="voucher" type="text" onClick={() => this.reset()}/></div>
                </div>
                {!!this.state.voucherError ?
                    <ErrorTag id="voucherError" error={this.state.voucherError}></ErrorTag> : ""}
                <div><input type="button" value="Register" onClick={() => this.register()}/></div>
            </div>
        );
    }
}
