import React from "react";
import {Login} from "./login";
import {Register} from "./register";
import './authentication.css';

enum choice {
    login,
    register
}

interface AuthProps {
    selection: choice;
}

export class Authentication extends React.Component<any, AuthProps> {
    constructor(props: any) {
        super(props);
        this.state = {
            selection: choice.login
        }
    }

    render() {
        return (
            <div className="canvas">
                <div className="logoName">Shangri-la Utility Management</div>
                <div className="authentication">
                    <div className="toggle">
                        <button className={this.state.selection === choice.login ? "selected" : ""}
                                onClick={() => this.setState({selection: choice.login})}>Sign In
                        </button>
                        <button className={this.state.selection === choice.register ? "selected" : ""}
                                onClick={() => this.setState({selection: choice.register})}>Register
                        </button>
                    </div>
                    {this.state.selection === choice.login ? <Login/> : <Register/>}
                </div>
            </div>
        );
    }
}
