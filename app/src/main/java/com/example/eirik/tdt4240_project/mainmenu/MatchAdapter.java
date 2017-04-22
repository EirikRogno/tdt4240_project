package com.example.eirik.tdt4240_project.mainmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.models.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchAdapter extends ArrayAdapter<Match>{

    private ArrayList<Match> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtPlayerOne;
        TextView txtPlayerTwo;
        TextView txtState;
    }

    public MatchAdapter(ArrayList<Match> matches, Context context){
        super(context, R.layout.match_list_item, matches);
        this.dataSet = matches;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Match match = dataSet.get(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.match_list_item, parent, false);
            viewHolder.txtPlayerOne = (TextView) convertView.findViewById(R.id.playerOne);
            viewHolder.txtPlayerTwo = (TextView) convertView.findViewById(R.id.playerTwo);
            viewHolder.txtState = (TextView) convertView.findViewById(R.id.state);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtPlayerOne.setText(match.getplayerOne());
        viewHolder.txtPlayerTwo.setText(match.getplayerTwo());

        String stateText = "";
        List<String> states = match.getAllowedStates();
        String state = match.getState();

        if(match.getplayerOne().equals(AppController.getInstance().getUsername())){
            if(state.equals(states.get(0))){
                stateText = "Your turn to draw!";
            }else if(state.equals(states.get(1))){
                stateText = "Opponents turn!";
            }else if(state.equals(states.get(2))){
                stateText = "Opponents turn!";
            }else if(state.equals(states.get(3))){
                stateText = "Your turn to guess!";
            }else if(state.equals(states.get(4))){
                stateText = "Accept invite?";
            }
        }else if(match.getplayerTwo().equals(AppController.getInstance().getUsername())){
            if(state.equals(states.get(0))){
                stateText = "Opponents turn!";
            }else if(state.equals(states.get(1))){
                stateText = "Your turn to guess!";
            }else if(state.equals(states.get(2))){
                stateText = "Your turn to draw!";
            }else if(state.equals(states.get(3))){
                stateText = "Opponents turn!";
            }else if(state.equals(states.get(4))){
                stateText = "Waiting for answer";
            }
        }

        viewHolder.txtState.setText(stateText);

        return convertView;
    }
}
