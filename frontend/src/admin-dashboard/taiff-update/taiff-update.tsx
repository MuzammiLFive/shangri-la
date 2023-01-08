import React from "react";
import "./taiff-update.css";
import {get, post} from "../../common/http";

interface Tprops {
    electricityDay?: number,
    electricityNight?: number,
    gas?: number
}

export class TaiffUpdate extends React.Component<any, Tprops> {
    constructor(props: any) {
        super(props);
        this.state = {
            electricityDay: undefined,
            electricityNight: undefined,
            gas: undefined,
        }
    }

    componentDidMount() {
        this.getTaiff();
    }

    getTaiff() {
        get('/admin/get-taiff')
            .then(response => response?.json() as unknown as Tprops)
            .then(json => this.setState({
                electricityDay: json.electricityDay,
                electricityNight: json.electricityNight,
                gas: json.gas
            }));
    }

    updateVals() {
        const elecDay = (document.getElementById("electricityDay") as HTMLInputElement).value as unknown as number;
        const elecNight = (document.getElementById("electricityNight") as HTMLInputElement).value as unknown as number;
        const gas = (document.getElementById("gas") as HTMLInputElement).value as unknown as number;
        this.setState({
            electricityDay: elecDay,
            electricityNight: elecNight,
            gas: gas
        });
    }

    updateTaiff() {
        const taiff: Tprops = {
            electricityDay: this.state.electricityDay,
            electricityNight: this.state.electricityNight,
            gas: this.state.gas
        }
        post("/admin/update-taiff", taiff)
            .then(response => response?.json())
            .then(json => alert(json["message"]))
            .catch(error => console.log(error))
    }

    render() {
        return (
            <div className="taiff">
                <div className="sub">Taiff Rates:</div>
                <div className="row heading">
                    <div>Electricity Day</div>
                    <div>Electricity Night</div>
                    <div>Gas</div>
                </div>
                <div className="row">
                    <div><input id="electricityDay"
                                type="number" value={this.state.electricityDay || ""}
                                onChange={() => this.updateVals()}/></div>
                    <div><input id="electricityNight"
                                type="number" value={this.state.electricityNight || ""}
                                onChange={() => this.updateVals()}/></div>
                    <div><input id="gas" type="number" value={this.state.gas || ""}
                                onChange={() => this.updateVals()}/></div>
                </div>
                <div className="row">
                    <div>
                        <button onClick={() => this.updateTaiff()}>Update</button>
                    </div>
                </div>
            </div>
        )
    }
}
