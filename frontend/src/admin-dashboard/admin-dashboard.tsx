import React from "react";
import "./admin-dashboard.css";
import {TaiffUpdate} from "./taiff-update/taiff-update";
import {Statistics} from "./statistics/statistics";
import {Readings} from "./readings/readings";

enum Sidebar {
    taiff,
    statistics,
    readings
}

interface Sprops {
    selection: Sidebar;
}

export class AdminDashboard extends React.Component<any, Sprops> {
    constructor(props: any) {
        super(props);
        this.state = {
            selection: Sidebar.taiff
        }
    }

    getSelectedContent() {
        switch (this.state.selection) {
            case Sidebar.taiff:
                return <TaiffUpdate/>
            case Sidebar.statistics:
                return <Statistics/>
            case Sidebar.readings:
                return <Readings/>
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
                    <button className={this.isActive(Sidebar.taiff)}
                            onClick={() => this.setSelection(Sidebar.taiff)}>Taiff
                    </button>
                    <button className={this.isActive(Sidebar.statistics)}
                            onClick={() => this.setSelection(Sidebar.statistics)}>Statistics
                    </button>
                    <button className={this.isActive(Sidebar.readings)}
                            onClick={() => this.setSelection(Sidebar.readings)}>Readings
                    </button>
                </div>
                <div className="content">
                    {this.getSelectedContent()}
                </div>
            </div>
        )
    }
}
