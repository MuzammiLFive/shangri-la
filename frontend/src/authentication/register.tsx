import React from "react";
import {ErrorTag} from "../error-tag/error-tag";
import './register.css';

const PropertyTypes = [
    "Detached",
    "Semi Detached",
    "Terraced",
    "Flat",
    "Cottage",
    "Bungalow",
    "Mansion"
]

interface RProps {
    emailError?: string;
    passwordError?: string;
    propertyTypeError?: string;
    voucherError?: string;
}

export class Register extends React.Component<any, RProps> {
    constructor(props: any) {
        super(props);
        this.state = {
            emailError: "",
            passwordError: "",
            propertyTypeError: "",
            voucherError: "",
        }
    }

    matchPasswords(): boolean {
        const pass1 = (document.getElementById("password1") as HTMLInputElement).value;
        const pass2 = (document.getElementById("password2") as HTMLInputElement).value;

        return pass1 === pass2;
    }

    hidePasswordError() {
        const pwd = document.getElementById("passwordError") as HTMLInputElement;
        pwd.hidden = true;
    }

    register() {
        if (!this.matchPasswords()) {
            this.setState({passwordError: "Passwords do not match."});
            console.log("test");
            return;
        }
    }

    render() {
        return (
            <form className="registration" action="#" onSubmit={() => this.register()}>
                <div className="cell">
                    <div>Email id: <input id="email" type="email" required/></div>
                    <div><ErrorTag id="usernameError" error={this.state.emailError}/></div>
                </div>
                <div className="cell">
                    <div>Password: <input id="password1" type="password" required
                                          onClick={() => this.hidePasswordError()}/></div>
                    <div hidden></div>
                </div>
                <div className="cell">
                    <div>Confirm Password: <input id="password2" type="password" required
                                                  onClick={() => this.hidePasswordError()}/></div>
                    <div><ErrorTag id="emailError" error={this.state.emailError}/></div>
                </div>
                <div className="cell">
                    <div>Address:</div>
                    <div><input id="address" type="text" required/></div>
                </div>
                <div className="cell">
                    <div>Property Type:
                        <select name="property_type" required>
                            <option value="">Select option</option>
                            {PropertyTypes.map(property => <option value={property}>{property}</option>)}
                        </select>
                    </div>
                    <div><ErrorTag id="propertyError" error={this.state.propertyTypeError}/></div>
                </div>
                <div className="cell">
                    <div>Number of Bedrooms: <input type="number" min="0" max="7" required/></div>
                    <div></div>
                </div>
                <div className="cell">
                    <div>Voucher code: <input type="text"/></div>
                    <div><ErrorTag id="voucherError" error={this.state.voucherError}></ErrorTag></div>
                </div>
                <div className="cell"><span><input type="submit" value="Log in"/></span></div>
            </form>
        );
    }
}
