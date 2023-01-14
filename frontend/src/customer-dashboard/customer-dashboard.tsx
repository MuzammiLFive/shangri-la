import React from "react";
import {SubmitReading} from "./submit-reading/submit-reading";
import {PayBill} from "./pay-bill/pay-bill";
import {Home} from "./home/home";

enum Sidebar {
    home,
    submitReading,
    payBill
}

interface Sprops {
    selection: Sidebar;
}

export class CustomerDashboard extends React.Component<any, Sprops> {
    constructor(props: any) {
        super(props);
        this.state = {
            selection: Sidebar.home
        }
    }

    getSelectedContent() {
        switch (this.state.selection) {
            case Sidebar.home:
                return <Home/>
            case Sidebar.submitReading:
                return <SubmitReading/>
            case Sidebar.payBill:
                return <PayBill/>
            default:
                return <div>Invalid!</div>
        }
    }

    setSelection(val: Sidebar) {
        this.setState({
            selection: val
        });
    }

    isActive(val: Sidebar): string {
        return this.state.selection === val ? "active" : ""
    }

    render() {
        return (
            <div className="main">
                <div className="sidebar">
                    <button className={this.isActive(Sidebar.home)}
                            onClick={() => this.setSelection(Sidebar.home)}>Home
                    </button>
                    <button className={this.isActive(Sidebar.submitReading)}
                            onClick={() => this.setSelection(Sidebar.submitReading)}>Submit Reading
                    </button>
                    <button className={this.isActive(Sidebar.payBill)}
                            onClick={() => this.setSelection(Sidebar.payBill)}>Pay Bill
                    </button>
                </div>
                <div className="content">
                    {this.getSelectedContent()}
                </div>
            </div>
        )
    }
}
