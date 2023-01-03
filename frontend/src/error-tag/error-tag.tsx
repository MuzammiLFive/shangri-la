import React from "react";

interface ErrorProps {
    id: string;
    error?: string;
}

export class ErrorTag extends React.Component<ErrorProps, ErrorProps> {
    constructor(props: ErrorProps) {
        super(props);
        this.state = {
            id: this.props.id,
            error: this.props.error
        }
    }

    render() {
        const error = this.state.error;
        return ( error ? <span>{this.state.error}</span> : "" )
    }
}
