import React from "react";
import {get} from "../../common/http";
import "./readings.css";

interface readingsProps {
    readings: reading[];
}

interface reading {
    readingId?: string;
    customerId?: string;
    submissionDate?: Date;
    elecReadingDay?: number;
    elecReadingNight?: number;
    gasReading?: number;
    status?: string;
}

export class Readings extends React.Component<any, readingsProps> {
    constructor(props: any) {
        super(props);
    }

    componentDidMount() {
        this.getReadings();
    }

    getReadings() {
        const list: reading[] = [];
        get("/admin/readings")
            .then(response => response?.json() as unknown as reading[])
            .then(values => {
                values.map(val => {
                    list.push(val);
                });
                this.setState({readings: list});
            });
    }

    getDate(date: any) {
        // @ts-ignore
        return Date(date).toString();
    }

    render() {
        return (
            <div className="admin-header">
                <div className="sub">Readings</div>
                {this.state?.readings ?
                    <div className="reading-table">
                        <div className="reading-row reading-header">
                            <div>Id</div>
                            <div>Customer</div>
                            <div>Submission Date</div>
                            <div>Electricity Reading Day</div>
                            <div>Electricity Reading Night</div>
                            <div>Gas Reading</div>
                            <div>Status</div>
                        </div>
                        {this.state.readings.map(reading =>
                            <div className="reading-row">
                                <div>{reading.readingId}</div>
                                <div>{reading.customerId}</div>
                                <div>{this.getDate(reading.submissionDate)}</div>
                                <div>{reading.elecReadingDay}</div>
                                <div>{reading.elecReadingNight}</div>
                                <div>{reading.gasReading}</div>
                                <div>{reading.status}</div>
                            </div>)}
                    </div>
                    : ""}
            </div>
        )
    }
}
