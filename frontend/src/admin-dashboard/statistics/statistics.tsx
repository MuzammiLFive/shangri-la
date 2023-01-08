import React from "react";
import {get} from "../../common/http";

interface StatProps {
    electricityAvg?: number;
    gasAvg?: number;
}

export class Statistics extends React.Component<any, StatProps> {

    constructor(props: any) {
        super(props);
        this.state = {
            electricityAvg: undefined,
            gasAvg: undefined
        }
    }

    componentDidMount() {
        this.getStatistics();
    }

    getStatistics() {
        get("/admin/statistics")
            .then(response => response?.json() as unknown as StatProps)
            .then(val => this.setState({
                electricityAvg: val.electricityAvg,
                gasAvg: val.gasAvg
            }));
    }

    render() {
        return (
            <div className="admin-header">
                <div className="sub">Statistics</div>
                <div>
                    <div>Average Electricity used during Day: <b>{this.state.electricityAvg} kWh</b></div>
                    <div>Average gas used: <b>{this.state.gasAvg} kWh</b></div>
                </div>
            </div>
        )
    }
}
