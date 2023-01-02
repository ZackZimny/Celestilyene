package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;
import com.mygdx.game.GameHelpers.Direction;
import com.mygdx.game.GameHelpers.Boxes.DynamicBox;

import java.util.ArrayList;

public class Player extends Entity {
    private Vector2 moveVec = new Vector2();
    private boolean moving = false;
    private Direction direction = Direction.UP;
    private Texture texture;
    private TextureRegion upSprite;
    private TextureRegion rightSprite;
    private TextureRegion leftSprite;
    private TextureRegion downSprite;
    private TextureRegion currSprite;
    private Texture magicTexture;
    private Texture wand;
    private float magicMeter = 1f;
    private ArrayList<DynamicBox> magicBoxes = new ArrayList<>();

    public Player(int x, int y) {
        super(new Box(x, y, 20, 22), new ArrayList<Box>());
        texture = new Texture(Gdx.files.internal("Player.png"));
        magicTexture = new Texture(Gdx.files.internal("Magic.png"));
        wand = new Texture(Gdx.files.internal("Wand.png"));
        TextureRegion[][] regions = TextureRegion.split(texture,
                texture.getWidth() / 4, texture.getHeight());
        //down right up left
        downSprite = regions[0][0];
        rightSprite = regions[0][1];
        upSprite = regions[0][2];
        leftSprite = regions[0][3];
        currSprite = upSprite;
        setTexture(texture);
    }

    public void updateMoveVec(){
        int[] keys = {Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D};
        Vector2[] moves = {new Vector2(0,1), new Vector2(0, -1),
                new Vector2(-1, 0), new Vector2(1, 0)};
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        moving = false;
        moveVec = new Vector2(0, 0);
        for(int i = 0; i < keys.length; i++){
            if(Gdx.input.isKeyPressed(keys[i])){
                moveVec = moves[i];
                moving = true;
                direction = directions[i];
            }
        }
    }

    private void handleAnimation(){
        Vector2 mouse = getMousePos();
        if(Math.abs(mouse.x) >= Math.abs(mouse.y)){
            if(mouse.x < 0){
                currSprite = leftSprite;
            } else {
                currSprite = rightSprite;
            }
        } else {
            if(mouse.y < 0){
                currSprite = downSprite;
            } else {
                currSprite = upSprite;
            }
        }
    }

    public void move(float deltaTime, ArrayList<Entity> unpassableEntities, ShapeRenderer shapeRenderer){
        float playerSpeed = 150f;
        updateMoveVec();
        handleAnimation();
        //moves the player according to which button is pressed so long as he is moving
        if (!moveVec.equals(new Vector2(0, 0))){
            setMovement(moveVec.cpy().nor().scl(playerSpeed * deltaTime));
        } else {
            //else statement prevents the normalizing of the Vector (0, 0), which if normalized will cause the
            //player to move
            setMovement(new Vector2(0, 0));
        }
        for(Entity e : unpassableEntities) {
            if(runInto(e)){
                setMovement(new Vector2(0, 0));
            }
            removeIntersectedHitBoxes(e);
        }
        changePos(getMovement());
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && magicMeter > 0){
            DynamicBox box = new DynamicBox(getHurtBox().getCenter().add(getMousePos().nor().scl(wand.getWidth())).sub(getLeftHandOffset(), 0),
                    10, 10, getMousePos().nor().scl(7));
            addHitBox(box);
            magicMeter -= 0.2f;
        }
        moveDynamicHitBoxes();
        magicMeter += 0.005f;
        if(magicMeter > 1){
            magicMeter = 1;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        Vector2 center = getHurtBox().getCenter();
        float xPos = center.x - currSprite.getRegionWidth() / 2f;
        float yPos = center.y - currSprite.getRegionHeight() / 2f;
        spriteBatch.draw(currSprite, xPos, yPos);
        for(Box hitBox : getHitBoxes()){
            spriteBatch.draw(magicTexture, hitBox.getX(), hitBox.getY(), 16, 16);
        }
        float leftHand = getLeftHandOffset();
        spriteBatch.draw(TextureRegion.split(wand, wand.getWidth(), wand.getHeight())[0][0],
                center.x - leftHand, center.y, wand.getWidth() / 2f,
                0, wand.getWidth(), wand.getHeight(), 1, 1, getMousePos().angleDeg() - 90);
    }


    private float getLeftHandOffset(){
        return getMousePos().x < 0 ? currSprite.getRegionWidth() / 2f : 0;
    }

    private Vector2 getMousePos(){
        return new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()).sub(getHurtBox().getCenter()).nor();
    }

}
