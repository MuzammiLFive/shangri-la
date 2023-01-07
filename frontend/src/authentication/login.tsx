import React from "react";
import './login.css';
import {preloginPost} from "../common/http";
import {ErrorTag} from "../error-tag/error-tag";

interface loginState {
    error?: string;
}

interface loginResponse {
    customerId: string,
    role: string,
    token: string,
}

export class Login extends React.Component<any, loginState> {
    constructor(props: any) {
        super(props);
        this.state = {
            error: undefined
        }
    }

    login() {
        const email = (document.getElementById("email") as HTMLInputElement).value;
        if (email === "") {
            this.setState({error: "Email cannot be empty"});
            return;
        }
        const password = (document.getElementById("password") as HTMLInputElement).value;
        if (password === "") {
            this.setState({error: "password cannot be empty"});
            return;
        }

        preloginPost("/api/login", {"email": email, "password": password})
            .then(response => {
                if (!response?.ok) {
                    this.setState({error: "Email or password invalid"})
                    throw new Error("Email or password invalid");
                }
                return response?.json() as unknown as loginResponse
            }).then(json => {
            sessionStorage.setItem('user', json.customerId);
            sessionStorage.setItem('role', json.role);
            sessionStorage.setItem('token', json.token);
            window.location.reload();
        }).catch(error => {
            console.log(error);
        });
    }

    toggleError() {
        this.setState({error: undefined});
    }

    render() {
        return (
            <div className="login">
                {!!this.state.error ? <ErrorTag id="1" error={this.state.error}/> : ""}
                <div className="cell">
                    <div>Email:</div>
                    <div><input id="email" type="email" required onClick={() => this.toggleError()}/>
                    </div>
                </div>
                <div className="cell">
                    <div>Password:</div>
                    <div><input id="password" type="password" required
                                onClick={() => this.toggleError()}/></div>
                </div>
                <div className="submit">
                    <input type="submit" value="Log in" onClick={() => this.login()}/>
                </div>
            </div>
        );
    }
}
